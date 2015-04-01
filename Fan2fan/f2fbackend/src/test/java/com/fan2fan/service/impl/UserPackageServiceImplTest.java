package com.fan2fan.service.impl;

import com.fan2fan.model.content.PackageTrip;
import com.fan2fan.model.content.UserPackage;
import com.fan2fan.service.packages.PackageService;
import com.fan2fan.util.ReflectionUtils;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test class for {@link com.fan2fan.service.packages.impl.UserPackageServiceImpl}
 * @author huangsz
 */
public class UserPackageServiceImplTest extends BaseServiceTest {

    @Autowired
    private PackageService packageService;

    private UserPackage userPackage;

    @Before
    public void setUp() {
        userPackage = new UserPackage();
        userPackage.setTitle("title");
        userPackage.setDesc("desc");
        userPackage.setLanguage("English");
        userPackage.setSummary("summary");
        userPackage.setPublished(false);
        userPackage.setCreatorId(1L);
        userPackage.setCreatorName("huangsz");
        userPackage.setCurrencyId(1L);
        userPackage.setPrice(30);
        userPackage.setItemTips(Lists.newArrayList("item1", "item2", "item3"));

        List<PackageTrip> trips = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PackageTrip trip = new PackageTrip();
            trip.setDesc("trip" + i);
            trip.setSummary("one day on the bar");
            trip.setPlace("Paris");
            trip.setImageUrl("2.png");
            trips.add(trip);
        }
        userPackage.setTrips(trips);
    }

    /**
     * test method for create, update and get package
     */
    @Test
    @Rollback(true)
    public void testCreateUpdateAndGetPackage() {
        packageService.createPackage(userPackage);
        assertThat(userPackage.getId() != null, is(true));

        // test get
        UserPackage another = packageService.getPackage(userPackage.getId());
        assertThat(another.getId(), equalTo(userPackage.getId()));
        assertThat(another.getTitle(), equalTo("title"));
        assertThat(another.getDesc(), equalTo("desc"));
        assertThat(another.getLanguage(), equalTo("English"));
        assertThat(another.getSummary(), equalTo("summary"));
        assertThat(another.getPublished(), equalTo(false));
        assertThat(another.getCreatorId(), equalTo(1L));
        assertThat(another.getCreatorName(), equalTo("huangsz"));
        assertThat(another.getCurrencyId(), equalTo(1L));
        assertThat(another.getPrice(), equalTo(30));
        assertThat(another.getViews(), equalTo(0));
        assertThat(another.getUps(), equalTo(0));
        assertThat(another.getDowns(), equalTo(0));
        assertThat(another.getItemTips().size(), equalTo(3));
        assertThat(another.getTrips().size(), equalTo(4));

        // test update
        UserPackage update = new UserPackage();
        update.setId(userPackage.getId()); // we don't update id and createTime
        update.setCreateTime(new Timestamp(System.currentTimeMillis()));
        update.setDowns(1);
        update.setPrice(50);
        packageService.updatePackage(update);

        another = packageService.getPackage(userPackage.getId());
        assertThat(another.getPrice(), equalTo(50));
        assertThat(another.getDowns(), equalTo(1));
        assertThat(another.getCreateTime(), lessThan(update.getCreateTime())); // createTime won't be updated
        assertThat(another.getUps(), equalTo(0)); // not updated

    }

    @Test
    public void testGetCreatedPackages() {
        UserPackage pkg = new UserPackage();
        ReflectionUtils.copyPropertiesIgnoreNull(userPackage, pkg);
        packageService.createPackage(userPackage);
        packageService.createPackage(pkg);
        assertThat(packageService.getCreatedPackages(userPackage.getCreatorId(), 0, 2).size(), equalTo(2));
        assertThat(packageService.getCreatedPackages(userPackage.getCreatorId(), 0, 1).size(), equalTo(1));

    }



}
