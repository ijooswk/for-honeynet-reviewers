__author__ = 'yuanchun'

import pydot
from PIL import Image

SMALI_BASIC_TYPES = {
        'V': "void",
        'Z': "boolean",
        'B': "byte",
        'S': 'short',
        'C': "char",
        'I': "int",
        'J': "long",
        'F': "float",
        'D': "double"
        }

def parseDesc(desc, static):
    name = desc.split('(', 1)[0]
    p0 = name.find("->")
    class_name = name[:p0]
    p1 = desc.find('(')
    p2 = desc.find(')')

    ret = desc[p2 + 1:]
    args = []
    paras = desc[p1 + 1:p2]
    index = 0
    dim = 0

    if not static:
        args.append(class_name)

    while index < len(paras):
        c = paras[index]
        if c == '[':
            dim += 1
            index += 1
        elif SMALI_BASIC_TYPES.has_key(c):
            args.append(paras[index - dim:index + 1])
            index += 1
            dim = 0
        else:
            tmp = paras.find(';', index)
            args.append(paras[index - dim:tmp + 1])
            index = tmp + 1
            dim = 0
    name = name.replace(";->", ": ")
    return name, args, ret

# CBG is short for callback graph
class CBG:
    def __init__(self):
        self.tag = "CBG"
        self.trigger_node = -1 # the trigger node of CBG, CBGnode_trigger
        self.callback_nodes = set() # the callback nodes of CBG, set of CBGnode_callback
        self.control_flow_edges = set() # the control flow edges of CBG, set of CBGedge_cfe
        self.data_flow_edges = set() # the data flow edges of CBG, set of CBGedge_data
        self.sequence_edges = set() # the sequence edges of CBG, set of CBGedge_seq

    def setTriggerNode(self, trigger_node):
        self.trigger_node = trigger_node

    def addCallbackNode(self, callback_node):
        self.callback_nodes.add(callback_node)

    def removeCallbackNode(self, callback_node):
        self.callback_nodes.remove(callback_node)

    def export2png(self):
        # cbg = pydot.Dot(graph_type="digraph", graph_name="callback graph of %s" % self.trigger_node.desc, fontname = "Chalkboard")
        #
        # trigger_cluster = pydot.Cluster(self.trigger_node.tag, shape = "record", fontname = "Chalkboard")
        # trigger_cluster.add_node(pydot.Node(self.trigger_node.tag, label = self.trigger_node.name, shape = "none"))
        # trigger_cluster.add_node(pydot.Node(self.trigger_node.ret.tag, label = self.trigger_node.ret.name))
        # for arg in self.trigger_node.args:
        #     trigger_cluster.add_node(pydot.Node(arg.tag, label = arg.name))
        # cbg.add_subgraph(trigger_cluster)
        #
        # callback_clusters = {}
        # for callback_node in self.callback_nodes:
        #     callback_cluster = pydot.Cluster(callback_node.tag, shape = "record", fontname = "Chalkboard")
        #     callback_cluster.add_node(pydot.Node(callback_node.tag, label = callback_node.name, shape = "none"))
        #     callback_cluster.add_node(pydot.Node(callback_node.ret.tag, label = callback_node.ret.name))
        #     for arg in callback_node.args:
        #         callback_cluster.add_node(pydot.Node(arg.tag, label = arg.name))
        #     cbg.add_subgraph(callback_cluster)
        #     callback_clusters[callback_node.tag] = callback_cluster
        #
        # for cfe in self.control_flow_edges:
        #     dot_cfe = pydot.Edge(cfe.source.tag, cfe.dest.tag, label=cfe.tag)
        #     cbg.add_edge(dot_cfe)
        #
        # for dfe in self.data_flow_edges:
        #     dot_dfe = pydot.Edge(dfe.source.tag, dfe.dest.tag, style = "dashed")
        #     cbg.add_edge(dot_dfe)

        cbg = pydot.Dot(graph_type="digraph", graph_name="callback graph of %s" % self.trigger_node.desc, \
                        fontname = "Chalkboard")
        labelstr = "{<head>%s" % self.trigger_node.name
        for arg in self.trigger_node.args:
            labelstr += "|<%s>%s" % (arg.tag, arg.name)
        labelstr += "|<%s>%s}" % (self.trigger_node.ret.tag, self.trigger_node.ret.name)
        node = pydot.Node(self.trigger_node.tag, shape="record", label=labelstr, fontname = "Chalkboard", rank="same")
        cbg.add_node(node)

        sub_cbg = pydot.Subgraph()
        for callback_node in self.callback_nodes:
            labelstr = "{<head>%s" % callback_node.name
            for arg in callback_node.args:
                labelstr += "|<%s>%s" % (arg.tag, arg.name)
            labelstr += "|<%s>%s}" % (callback_node.ret.tag, callback_node.ret.name)
            node = pydot.Node(callback_node.tag, shape="record", label=labelstr, fontname = "Chalkboard")
            sub_cbg.add_node(node)
        cbg.add_subgraph(sub_cbg)

        cfes = set()

        for cfe in self.control_flow_edges:
            source_tag = cfe.source.tag
            dest_tag = cfe.dest.tag
            pair_tag1 = "%s_%s" % (source_tag, dest_tag)
            pair_tag2 = "%s_%s" % (dest_tag, source_tag)
            if pair_tag2 in cfes:
                cfes.remove(pair_tag2)
                dot_cfe = pydot.Edge(source_tag, dest_tag, color = "red", dir = "both")
                cbg.add_edge(dot_cfe)
            else:
                cfes.add(pair_tag1)
        for cfe in cfes:
            source_tag, dest_tag = cfe.split('_')
            dot_cfe = pydot.Edge(source_tag, dest_tag, color = "red")
            cbg.add_edge(dot_cfe)

        dfes = set()
        for dfe in self.data_flow_edges:
            source_tag = "%s:%s" % (dfe.source.belongs.tag, dfe.source.tag)
            dest_tag = "%s:%s" % (dfe.dest.belongs.tag, dfe.dest.tag)
            pair_tag1 = "%s_%s" % (source_tag, dest_tag)
            pair_tag2 = "%s_%s" % (dest_tag, source_tag)
            if pair_tag2 in dfes:
                dfes.remove(pair_tag2)
                dot_dfe = pydot.Edge(source_tag, dest_tag, dir = "both")
                cbg.add_edge(dot_dfe)
            else:
                dfes.add(pair_tag1)
        for dfe in dfes:
            source_tag, dest_tag = dfe.split('_')
            dot_dfe = pydot.Edge(source_tag, dest_tag)
            cbg.add_edge(dot_dfe)

        cbg.write("temp.png", prog="dot", format="png")
        im = Image.open("temp.png")
        im.show()


    def getAllCBP(self):
        allCBP = set()
        return allCBP

    def genConserCBG(self):
        # generate conservative control flow edges
        for callback_node in self.callback_nodes:
            CBGedge_cfe_call = CBGedge_cfe(self.trigger_node, callback_node)
            CBGedge_cfe_return = CBGedge_cfe(callback_node, self.trigger_node)
            self.control_flow_edges.add(CBGedge_cfe_call)
            self.control_flow_edges.add(CBGedge_cfe_return)

        # generate conservative data flow edges
        # step1 : each pair of args in trigger node have 2-way data flow edge
        for trigger_arg1 in self.trigger_node.args:
            for trigger_arg2 in self.trigger_node.args:
                if trigger_arg1 != trigger_arg2:
                    dfe = CBGedge_dfe(trigger_arg1, trigger_arg2)
                    self.data_flow_edges.add(dfe)
        # step2 : for each pair of nodes, the args in one node has a data flow with each arg in another node
        for callback_node in self.callback_nodes:
            for callback_arg in callback_node.args:
                for trigger_arg in self.trigger_node.args:
                    dfe1 = CBGedge_dfe(trigger_arg, callback_arg)
                    dfe2 = CBGedge_dfe(callback_arg, trigger_arg)
                    self.data_flow_edges.add(dfe1)
                    self.data_flow_edges.add(dfe2)
        for callback_node1 in self.callback_nodes:
            for callback_node2 in self.callback_nodes:
                if callback_node1 == callback_node2:
                    continue
                for callback1_arg in callback_node1.args:
                    for callback2_arg in callback_node2.args:
                        dfe1 = CBGedge_dfe(callback1_arg, callback2_arg)
                        dfe2 = CBGedge_dfe(callback2_arg, callback1_arg)
                        self.data_flow_edges.add(dfe1)
                        self.data_flow_edges.add(dfe2)
        # step3 : ret in callback node has data flow connection to all args in other node
        for callback_node in self.callback_nodes:
            for trigger_arg in self.trigger_node.args:
                dfe = CBGedge_dfe(callback_node.ret, trigger_arg)
                self.data_flow_edges.add(dfe)
                for other_callback_node in self.callback_nodes:
                    if other_callback_node == callback_node:
                        continue
                    for other_callback_node_arg in other_callback_node.args:
                        dfe = CBGedge_dfe(callback_node.ret, other_callback_node_arg)
                        self.data_flow_edges.add(dfe)
        # step4 : ret in trigger node has data flow connection from all other variables
        trigger_ret = self.trigger_node.ret
        for trigger_arg in self.trigger_node.args:
            dfe = CBGedge_dfe(trigger_arg, trigger_ret)
            self.data_flow_edges.add(dfe)
        for callback_node in self.callback_nodes:
            dfe = CBGedge_dfe(callback_node.ret, trigger_ret)
            self.data_flow_edges.add(dfe)
            for callback_arg in callback_node.args:
                dfe = CBGedge_dfe(callback_arg, trigger_ret)
        # [done] generate conservative data flow edges

    def printCBGinfo(self):
        print "NODES"
        print "[trigger node]%s\n[TAG]%s" % (self.trigger_node.desc, self.trigger_node.tag)
        print "  [variables]"
        for arg in self.trigger_node.args:
            print "  [%s]%s" % (arg.tag, arg.desc)
        print "  [%s]%s" % (self.trigger_node.ret.tag, self.trigger_node.ret.desc)
        for callback_node in self.callback_nodes:
            print "[callback node]%s\n[TAG]%s" % (callback_node.desc, callback_node.tag)
            print "  [variables]"
            for arg in callback_node.args:
                print "  [%s]%s" % (arg.tag, arg.desc)
            print "  [%s]%s" % (callback_node.ret.tag, callback_node.ret.desc)

        print "\nEDGES"
        print "[control flow edges]"
        for cfe in self.control_flow_edges:
            print "  %s --> %s" % (cfe.source.name, cfe.dest.name)
        print "[data flow edges]"
        for dfe in self.data_flow_edges:
            print "  %s.%s --> %s.%s" % (dfe.source.belongs.name, dfe.source.name, dfe.dest.belongs.name, dfe.dest.name)
        print "[sequence edges]"
        for seq in self.sequence_edges:
            print "  %s --> %s" % (seq.source.name, seq.dest.name)

# CBP is short for callback path
class CBP:
    def __init__(self):
        self.nodes = [] # CBGnodes on this path
        self.data_flows = {} # data flows on this path


class CBGnode:
    def __init__(self):
        self.type = -1 # 0 for trigger node, 1 for callback node, int
        self.desc = "" # method description, string
        self.tag = "" # tag of node, string
        self.name = "" # method name, string
        self.args = [] # argument variable of this node, CBGvariable_arg
        self.ret = -1 # return variable of this node, CBGvariable_ret

class CBGnode_trigger(CBGnode):
    id = 0
    def __init__(self, desc, static):
        self.type = 0
        self.desc = desc
        self.tag = "trigger%d" % CBGnode_trigger.id
        CBGnode_trigger.id += 1
        self.args = []
        self.name, args, ret = parseDesc(desc, static)
        id = 0
        for arg in args:
            arg_node = CBGvariable_arg(self, arg, id)
            self.args.append(arg_node)
            id = id + 1
        self.ret = CBGvariable_ret(self, ret)

class CBGnode_callback(CBGnode):
    id = 0
    def __init__(self, desc, static):
        self.type = 1
        self.desc = desc
        self.tag = "callback%d" % CBGnode_callback.id
        CBGnode_callback.id += 1
        self.args = []
        self.name, args, ret = parseDesc(desc, static)
        id = 0
        for arg in args:
            arg_node = CBGvariable_arg(self, arg, id)
            self.args.append(arg_node)
            id = id + 1
        self.ret = CBGvariable_ret(self, ret)

class CBGvariable:
    def __init__(self):
        self.type = -1 # 0 for arguments, 1 for return, int
        self.belongs = -1 # the node which this variable belongs to, CBGnode
        self.desc = "" # description of this variable, String
        self.tag = "" # string

class CBGvariable_arg(CBGvariable):
    def __init__(self, parent, desc, id):
        self.belongs = parent
        self.type = 0
        self.desc = desc
        self.id = id
        self.tag = "%sarg%d" % (parent.tag, id)
        self.name = "arg%d: %s" % (id, self.desc)

    def convertFromArgArray(args, parent, id):
        variable_args = []
        for arg in args:
            variable_arg = CBGvariable_arg(parent, arg, id)
            variable_args.append(variable_arg)
        return variable_args

class CBGvariable_ret(CBGvariable):
    def __init__(self, parent, desc):
        self.belongs = parent
        self.type = 1
        self.desc = desc
        self.tag = "%sret" % parent.tag
        self.name = "ret: %s" % self.desc

class CBGedge:
    def __init__(self):
        self.type = -1 # 0 for control flow edge, 1 for sequence edge, 2 for data flow edge
        self.source = -1 # source of edge, CBGnode or CBGvariable
        self.dest = -1 # dest of edge, CBGnode or CBGvariable
        self.tag = "edge"

class CBGedge_cfe(CBGedge):
    def __init__(self, source, dest):
        self.type = 0
        self.source = source
        self.dest = dest
        self.tag = "%s_cfe_%s" % (source.tag, dest.tag)

class CBGedge_seq(CBGedge):
    def __init__(self, source, dest):
        self.type = 1
        self.source = source
        self.dest = dest
        self.tag = "%s_seq_%s" % (source.tag, dest.tag)

class CBGedge_dfe(CBGedge):
    def __init__(self, source, dest):
        self.type = 2
        self.source = source
        self.dest = dest
        self.tag = "%s_dfe_%s" % (source.tag, dest.tag)

example_trigger_desc = "Lcom/lynn/AppAsyncTask;->execute(Ljava/lang/String;)Landroid/os/AsyncTask;"
example_callback_descs = ["Lcom/lynn/AppAsyncTask;->doInBackground(Ljava/lang/String;)J",
                          "Lcom/lynn/AppAsyncTask;->onPostExecute(J)V"]

name, args, ret = parseDesc(example_trigger_desc, False)
print name
for arg in args:
    print arg
print ret

cbg = CBG()

trigger = CBGnode_trigger(example_trigger_desc, False)
cbg.setTriggerNode(trigger)

for callback_desc in example_callback_descs:
    callback = CBGnode_callback(callback_desc, False)
    cbg.addCallbackNode(callback)

cbg.genConserCBG()

cbg.printCBGinfo()

cbg.export2png()