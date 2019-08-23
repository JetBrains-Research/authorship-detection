package com.lz.game;
import cn.teaey.fenrisulfr.cache.AutoSavedCache;
import cn.teaey.fenrisulfr.cache.DataCache;
import cn.teaey.fenrisulfr.cache.KeyType;
import cn.teaey.fenrisulfr.orm.JDBCConfig;
import cn.teaey.fenrisulfr.orm.StandardDao;
import org.junit.Before;
import org.junit.Test;
/**
 * User: Teaey
 * Date: 13-6-9
 */
public class TestSample
{
    @Before
    public void before()
    {
    }
    @Test
    public void testRelationDescGen()
    {
        StandardDao dao = StandardDao.getInstance();
        Player p = dao.select(Player.class, 1);
        System.out.println(p.getItem().size());
    }
    @Test
    public void insert()
    {
        Player player = new Player();
        player.setName("player2");
        StandardDao.getInstance().insert(player);
        Player playerLoad = StandardDao.getInstance().select(Player.class, player.getId());
        System.out.println(player.equals(playerLoad));
    }
    @Test
    public void delete()
    {
        StandardDao.getInstance().deleteById(Player.class, 3);
    }
    @Test
    public void select()
    {
        Player log = StandardDao.getInstance().select(Player.class, 4);
        //System.out.println(log.getItem());
    }
    @Test
    public void update()
    {
        Player player = StandardDao.getInstance().select(Player.class, 4);
        System.out.println(player.getItem().size());
        player.getItem().get(0).setName("第二个物品");
        StandardDao.getInstance().update(player);
    }
    @Test
    public void getItem()
    {
        DataCache cache = new AutoSavedCache();
        Item item = cache.get(Item.class, 2, KeyType.PRIMARY_KEY);
        System.out.println(item);
        cache.update(item);
        while (true)
        {
            try
            {
                Thread.sleep(1212121L);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
