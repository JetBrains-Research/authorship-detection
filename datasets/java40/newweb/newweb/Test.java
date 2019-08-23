import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newweb.model.business.Bill;
import com.newweb.model.business.Order;
import com.newweb.service.business.BillService;
import com.newweb.service.business.OrderService;




public class Test {

	
	
	@org.junit.Test
	public void test() throws IOException {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		PrintService ps = (PrintService) ctx.getBean("printService");
//		ps.printMaterialAndGlassContent("a071c83b-f434-47c4-805c-5ff9c90aede4");
		
//		OrderPriceService orderPriceService = (OrderPriceService) ctx.getBean("orderPriceService");
//		OrderBxgService orderBxgService = (OrderBxgService) ctx.getBean("orderBxgService");
//		BxgService BxgService = (BxgService) ctx.getBean("bxgService");
//		OrderBxg[] obs = orderBxgService.queryAllOrderBxgs();
//		for (OrderBxg ob : obs) {
//			OrderPrice op = new OrderPrice();
//			op.setOrder(ob.getOrder());
//			op.setProductID(ob.getBxg().getID());
//			op.setType("bxg");
//			double price = BxgService.findBxgByIdBindCut(ob.getBxg().getID(), 
//					ob.getOrder().getCustomer().getID(), ob.getOrder().getID()).getLsprice();
//			op.setPrice(price);
//			if(orderPriceService.save(op)){
//				System.out.println(ob.getID());
//			}
//		}
		
//		OrderService os = (OrderService) ctx.getBean("orderService");
//		BillService billService = (BillService) ctx.getBean("billService");
//		Bill[] bs = billService.queryAllBills();
//		for (Bill bill : bs) {
//			Order[] orders = billService.queryOrdersByBillId(bill.getID());
//			for (Order order : orders) {
//				if(order.getRemark().contains("历史")){
//					String[] ss = order.getRemark().split("-");
//					if(ss.length >=3){
//						Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
//						Matcher m = p.matcher(order.getRemark());
//						while(m.find()){
//							String s = m.group();
//							if(!order.getCreateDate().equals(s)){
//								System.out.println(s);
//								order.setCreateDate(s);
//								if(os.modifyOrder(order)){
//									System.out.println("Order:" + order.getID());
//								}
//								bill.setCreateDate(s);
//								bill.setLastModifyDate(s);
//								if(billService.modify(bill)){
//									System.out.println("Bill:" + bill.getID());
//								}
//							}
//						}
//					}
//				}
//			}
//		}
		
		
		//mysqldump  -u root -p newwebdb > C:\Users\Administrator\Desktop\sql.sql
		InputStream in = Runtime.getRuntime().exec("mysqldump").getInputStream();
		byte[] data = new byte[100000];
		int i = 0;
		int read = in.read();
		while(read != -1){
			data[i] = (byte) read;
			i++;
			read = in.read();
		}
		String s = new String(data, 0, i);
		in.close();
		System.out.println(s);
		
		
//		随即打乱订单客户
//		OrderService os = (OrderService) ctx.getBean("orderService");
//		CustomerService cs = (CustomerService) ctx.getBean("customerService");
//		Order[] orders = os.queryAllOrders();
//		for (Order order : orders) {
//			int i = new Random().nextInt(109);
//			while(cs.findCustomerByID(i) == null){
//				i = new Random().nextInt(109);
//			}
//			order.setCustomer(cs.findCustomerByID(i));
//			os.modifyOrder(order);
//		}
		
//		Set<OrderSmall> set = new HashSet<OrderSmall>();
//		OrderSmall os1 = new OrderSmall();
//		OrderSmall os2 = new OrderSmall();
//		Small s1 = new Small();
//		s1.setID(1);
//		Small s2 = new Small();
//		s2.setID(1);
//		os1.setSmall(s1);
//		os2.setSmall(s2);
//		set.add(os1);
//		System.out.println(set.add(os2));
//		Object[] o = set.toArray();
//		for(Iterator<OrderSmall> it = set.iterator();it.hasNext();){
//			System.out.println(it.next().getSmall().getID());
//		}
		
	}

}
