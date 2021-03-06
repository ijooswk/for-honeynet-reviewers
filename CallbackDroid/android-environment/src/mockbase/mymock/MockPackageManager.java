package mymock;

import mymock.mockContent.pm.MockApplicationInfo;

import android.content.pm.PackageManager;
import android.annotation.SdkConstant;
import android.annotation.SdkConstant.SdkConstantType;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AndroidException;
import android.util.DisplayMetrics;

import java.io.File;
import java.util.List;

import android.content.pm.PackageInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ActivityInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.InstrumentationInfo;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageDataObserver;

public class MockPackageManager extends PackageManager{

	private MockPackageInfo pi;
	private ApplicationInfo ai;
	
	public void setApplicationInfo(MockApplicationInfo apinfo) {
		ai = apinfo;
	}
	
	public void setPackageInfo(MockPackageInfo pifo){
		pi = pifo;
	}

	@Override
	public  PackageInfo getPackageInfo(String packageName, int flags)
				throws NameNotFoundException{
		return pi;
	}

	@Override
	public String[] currentToCanonicalPackageNames(String[] names){
		return null;
	}

	@Override
	public String[] canonicalToCurrentPackageNames(String[] names){
		return null;
	}

	@Override
	public Intent getLaunchIntentForPackage(String packageName){
		return null;
	}

	@Override
	public int[] getPackageGids(String packageName)
	            throws NameNotFoundException{
		return null;
	}

	@Override
	public PermissionInfo getPermissionInfo(String name, int flags)
	            throws NameNotFoundException{
		return null;
	}

	@Override
	public List<PermissionInfo> queryPermissionsByGroup(String group,
			            int flags) throws NameNotFoundException{
		return null;
	}

	@Override
	public PermissionGroupInfo getPermissionGroupInfo(String name,
			            int flags) throws NameNotFoundException{
		return null;
	}

	@Override
	public List<PermissionGroupInfo> getAllPermissionGroups(int flags){
		return null;
	}

	@Override
	public ApplicationInfo getApplicationInfo(String packageName,
			            int flags) throws NameNotFoundException{
		return ai;
	}

	@Override
	public ActivityInfo getActivityInfo(ComponentName component,
			            int flags) throws NameNotFoundException{
		return null;
	}

	@Override
	public ActivityInfo getReceiverInfo(ComponentName component,
			            int flags) throws NameNotFoundException{
		return null;
	}

	@Override
	public ServiceInfo getServiceInfo(ComponentName component,
			            int flags) throws NameNotFoundException{
		return null;
	}

	@Override
	public ProviderInfo getProviderInfo(ComponentName component,
			            int flags) throws NameNotFoundException{
		return null;
	}
	
	@Override
	public  List<PackageInfo> getInstalledPackages(int flags){
		return null;
	}

	@Override
    public  int checkPermission(String permName, String pkgName){
		return 1;
	}
	
	@Override
    public  boolean addPermission(PermissionInfo info){
		return true;
	}
	
	@Override
	public boolean addPermissionAsync(PermissionInfo info){
		return true;
	}
	
	@Override
	public void removePermission(String name){
	}
	
	@Override
    public  int checkSignatures(String pkg1, String pkg2){
		return 1;
	}
	
	@Override
    public  int checkSignatures(int uid1, int uid2){
		return 1;
	}
	
	@Override
    public  String[] getPackagesForUid(int uid){
		return null;
	}
	
	@Override
    public  String getNameForUid(int uid){
		return null;
	}
    
    @Override
    public  int getUidForSharedUser(String sharedUserName)
            throws NameNotFoundException{
		return 1;
	}
	
	@Override
    public  List<ApplicationInfo> getInstalledApplications(int flags){
		return null;
	}
    
    @Override
    public  String[] getSystemSharedLibraryNames(){
		return null;
	}
	
	@Override
    public  FeatureInfo[] getSystemAvailableFeatures(){
		return null;
	}
	
	@Override
    public  boolean hasSystemFeature(String name){
		return true;
	}
	
	@Override
    public  ResolveInfo resolveActivity(Intent intent, int flags){
		return null;
	}

	@Override
    public  List<ResolveInfo> queryIntentActivities(Intent intent,
            int flags){
		return null;
	}

	@Override
    public  List<ResolveInfo> queryIntentActivityOptions(
            ComponentName caller, Intent[] specifics, Intent intent, int flags){
		return null;
	}

	@Override
    public  List<ResolveInfo> queryBroadcastReceivers(Intent intent,
            int flags){
		return null;
	}

	@Override
    public  ResolveInfo resolveService(Intent intent, int flags){
		return null;
	}

	@Override
    public  List<ResolveInfo> queryIntentServices(Intent intent,
            int flags){
		return null;
	}

	@Override
    public  ProviderInfo resolveContentProvider(String name,
            int flags){
		return null;
	}

	@Override
    public  List<ProviderInfo> queryContentProviders(
            String processName, int uid, int flags){
		return null;
	}

	@Override
    public  InstrumentationInfo getInstrumentationInfo(
            ComponentName className, int flags) throws NameNotFoundException{
		return null;
	}

	@Override
    public  List<InstrumentationInfo> queryInstrumentation(
            String targetPackage, int flags){
		return null;
	}

	@Override
    public  Drawable getDrawable(String packageName, int resid,
            ApplicationInfo appInfo){
		return null;
	}

	@Override
    public  Drawable getActivityIcon(ComponentName activityName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Drawable getActivityIcon(Intent intent)
            throws NameNotFoundException{
		return null;
	}
	
	@Override
    public  Drawable getDefaultActivityIcon(){
		return null;
	}

	@Override
    public  Drawable getApplicationIcon(ApplicationInfo info){
		return null;
	}

	@Override
    public  Drawable getApplicationIcon(String packageName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Drawable getActivityLogo(ComponentName activityName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Drawable getActivityLogo(Intent intent)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Drawable getApplicationLogo(ApplicationInfo info){
		return null;
	}

	@Override
    public  Drawable getApplicationLogo(String packageName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  CharSequence getText(String packageName, int resid,
            ApplicationInfo appInfo){
		return null;
	}

	@Override
    public  XmlResourceParser getXml(String packageName, int resid,
            ApplicationInfo appInfo){
		return null;
	}

	@Override
    public  CharSequence getApplicationLabel(ApplicationInfo info){
		return null;
	}

	@Override
    public  Resources getResourcesForActivity(ComponentName activityName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Resources getResourcesForApplication(ApplicationInfo app)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  Resources getResourcesForApplication(String appPackageName)
            throws NameNotFoundException{
		return null;
	}

	@Override
    public  void installPackage(
            Uri packageURI, IPackageInstallObserver observer, int flags,
            String installerPackageName){
	}

	@Override
    public  void deletePackage(
            String packageName, IPackageDeleteObserver observer, int flags){
	}

	@Override
    public  String getInstallerPackageName(String packageName){
		return null;
	}

	@Override    
    public  void clearApplicationUserData(String packageName,
            IPackageDataObserver observer){
	}

	@Override
    public  void deleteApplicationCacheFiles(String packageName,
            IPackageDataObserver observer){
	}

	@Override
    public  void freeStorageAndNotify(long freeStorageSize, IPackageDataObserver observer){
	}

	@Override
    public  void freeStorage(long freeStorageSize, IntentSender pi){
	}

	@Override
    public  void getPackageSizeInfo(String packageName,
            IPackageStatsObserver observer){
	}

    @Deprecated
    public  void addPackageToPreferred(String packageName){
	}

    @Deprecated
    public  void removePackageFromPreferred(String packageName){
	}
	
	@Override
    public  List<PackageInfo> getPreferredPackages(int flags){
    	return null;
	}

    @Deprecated
    public  void addPreferredActivity(IntentFilter filter, int match,
            ComponentName[] set, ComponentName activity){
	}

    @Deprecated
    public  void replacePreferredActivity(IntentFilter filter, int match,
            ComponentName[] set, ComponentName activity){
	}

	@Override
    public  void clearPackagePreferredActivities(String packageName){
	}

	@Override
    public  int getPreferredActivities(List<IntentFilter> outFilters,
            List<ComponentName> outActivities, String packageName){
		return 1;
	}

	@Override
    public  void setComponentEnabledSetting(ComponentName componentName,
            int newState, int flags){
	}

	@Override
    public  int getComponentEnabledSetting(ComponentName componentName){
		return 1;
	}

	@Override
    public  void setApplicationEnabledSetting(String packageName,
            int newState, int flags){
	}

	@Override    
    public  int getApplicationEnabledSetting(String packageName){
		return 1;
	}

	@Override
    public  boolean isSafeMode(){
		return true;
	}

	@Override
    public  void movePackage(
            String packageName, IPackageMoveObserver observer, int flags){
	}
}
