package cn.abovesky.shopping.dao;

import cn.abovesky.shopping.domain.Block;
import cn.abovesky.shopping.domain.Layer;
import cn.abovesky.shopping.domain.Room;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by snow on 2014/4/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class TestDao {
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private LayerMapper layerMapper;
    @Autowired
    private RoomMapper roomMapper;

    @Test
    public void testInsertBlock() throws Exception {
        for (int i = 1; i < 14; i++) {
            Block block = new Block();
            block.setAreaId(1);
            block.setName(String.valueOf(i));
            blockMapper.insert(block);
        }
    }

    @Test
    public void testInsertLayer() throws Exception {
        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 7; j++) {
                Layer layer = new Layer();
                layer.setName(String.valueOf(j));
                layer.setBlockId(i);
                layerMapper.insert(layer);
            }
        }
    }

    @Test
    public void testInsertRoom() throws Exception {
        for (int i = 35; i < 79; i++) {
            for (int j = 1; j < 31; j++) {
                Room room = new Room();
                room.setName((i % 6 == 0 ? 6 : i % 6) + (j < 10 ? "0" : "") + j);
                room.setLayerId(i);
                roomMapper.insert(room);
            }
        }
    }
}
