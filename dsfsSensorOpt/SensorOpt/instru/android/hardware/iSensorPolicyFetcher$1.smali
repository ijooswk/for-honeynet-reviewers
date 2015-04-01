.class Linstru/android/hardware/iSensorPolicyFetcher$1;
.super Ljava/lang/Object;
.source "iSensorPolicyFetcher.java"

# interfaces
.implements Landroid/content/ServiceConnection;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Linstru/android/hardware/iSensorPolicyFetcher;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Linstru/android/hardware/iSensorPolicyFetcher;


# direct methods
.method constructor <init>(Linstru/android/hardware/iSensorPolicyFetcher;)V
    .locals 0
    .parameter "this$0"

    .prologue
    .line 28
    iput-object p1, p0, Linstru/android/hardware/iSensorPolicyFetcher$1;->this$0:Linstru/android/hardware/iSensorPolicyFetcher;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onServiceConnected(Landroid/content/ComponentName;Landroid/os/IBinder;)V
    .locals 2
    .parameter "name"
    .parameter "service"

    .prologue
    .line 46
    iget-object v0, p0, Linstru/android/hardware/iSensorPolicyFetcher$1;->this$0:Linstru/android/hardware/iSensorPolicyFetcher;

    const/4 v1, 0x1

    #setter for: Linstru/android/hardware/iSensorPolicyFetcher;->mBound:Z
    invoke-static {v0, v1}, Linstru/android/hardware/iSensorPolicyFetcher;->access$002(Linstru/android/hardware/iSensorPolicyFetcher;Z)Z

    .line 47
    return-void
.end method

.method public onServiceDisconnected(Landroid/content/ComponentName;)V
    .locals 2
    .parameter "name"

    .prologue
    .line 33
    iget-object v0, p0, Linstru/android/hardware/iSensorPolicyFetcher$1;->this$0:Linstru/android/hardware/iSensorPolicyFetcher;

    iget-object v0, v0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    const-string v1, "Service has unexpectedly disconnected!"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 36
    iget-object v0, p0, Linstru/android/hardware/iSensorPolicyFetcher$1;->this$0:Linstru/android/hardware/iSensorPolicyFetcher;

    const/4 v1, 0x0

    #setter for: Linstru/android/hardware/iSensorPolicyFetcher;->mBound:Z
    invoke-static {v0, v1}, Linstru/android/hardware/iSensorPolicyFetcher;->access$002(Linstru/android/hardware/iSensorPolicyFetcher;Z)Z

    .line 38
    return-void
.end method
