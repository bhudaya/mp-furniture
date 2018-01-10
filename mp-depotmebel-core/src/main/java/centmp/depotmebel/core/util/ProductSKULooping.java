package centmp.depotmebel.core.util;

import java.util.ArrayList;
import java.util.List;

import centmp.depotmebel.core.model.ProductAttr;

public class ProductSKULooping {
	
	public List<String> result(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		int size = productAttrList.size();
		if(size==1){
			list = size1(productAttrList);
		}else if(size==2){
			list = size2(productAttrList);
		}else if(size==3){
			list = size3(productAttrList);
		}else if(size==4){
			list = size4(productAttrList);
		}else if(size==5){
			list = size5(productAttrList);
		}
		
		return list;
	}
	
	private List<String> size1(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		for(ProductAttr prodAtrr: productAttrList){
			String value = prodAtrr.getValue();
			System.out.println("Val: "+value);
			String[] split = value.split(",");
			for (String item: split) {
				System.out.println("item: "+item);
				list.add(item);
			}
		}
		
		return list;
	}
	
	private List<String> size2(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		ProductAttr prodAtrr1 = productAttrList.get(0);
		String value1 = prodAtrr1.getValue();
		String[] split1 = value1.split(",");
		
		ProductAttr prodAtrr2 = productAttrList.get(1);
		String value2 = prodAtrr2.getValue();
		String[] split2 = value2.split(",");
		
		for (String string1: split1) {
			for (String string2: split2) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(string1.trim());
				buffer.append(" ");
				buffer.append(string2.trim());
				list.add(buffer.toString().trim());
			}
		}
		
		
		return list;
	}
	
	private List<String> size3(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		ProductAttr prodAtrr1 = productAttrList.get(0);
		String value1 = prodAtrr1.getValue();
		String[] split1 = value1.split(",");
		
		ProductAttr prodAtrr2 = productAttrList.get(1);
		String value2 = prodAtrr2.getValue();
		String[] split2 = value2.split(",");
		
		ProductAttr prodAtrr3 = productAttrList.get(2);
		String value3 = prodAtrr3.getValue();
		String[] split3 = value3.split(",");
		
		for (String string1: split1) {
			for (String string2: split2) {
				for (String string3: split3) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(string1.trim());
					buffer.append(" ");
					buffer.append(string2.trim());
					buffer.append(" ");
					buffer.append(string3.trim());
					list.add(buffer.toString().trim());
				}
			}
		}
		
		
		return list;
	}
	
	private List<String> size4(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		ProductAttr prodAtrr1 = productAttrList.get(0);
		String value1 = prodAtrr1.getValue();
		String[] split1 = value1.split(",");
		
		ProductAttr prodAtrr2 = productAttrList.get(1);
		String value2 = prodAtrr2.getValue();
		String[] split2 = value2.split(",");
		
		ProductAttr prodAtrr3 = productAttrList.get(2);
		String value3 = prodAtrr3.getValue();
		String[] split3 = value3.split(",");
		
		ProductAttr prodAtrr4 = productAttrList.get(3);
		String value4 = prodAtrr4.getValue();
		String[] split4 = value4.split(",");
		
		for (String string1: split1) {
			for (String string2: split2) {
				for (String string3: split3) {
					for (String string4: split4) {
						StringBuffer buffer = new StringBuffer();
						buffer.append(string1.trim());
						buffer.append(" ");
						buffer.append(string2.trim());
						buffer.append(" ");
						buffer.append(string3.trim());
						buffer.append(" ");
						buffer.append(string4.trim());
						list.add(buffer.toString().trim());
					}
				}
			}
		}
		
		return list;
	}
	
	private List<String> size5(List<ProductAttr> productAttrList){
		List<String> list = new ArrayList<>();
		
		ProductAttr prodAtrr1 = productAttrList.get(0);
		String value1 = prodAtrr1.getValue();
		String[] split1 = value1.split(",");
		
		ProductAttr prodAtrr2 = productAttrList.get(1);
		String value2 = prodAtrr2.getValue();
		String[] split2 = value2.split(",");
		
		ProductAttr prodAtrr3 = productAttrList.get(2);
		String value3 = prodAtrr3.getValue();
		String[] split3 = value3.split(",");
		
		ProductAttr prodAtrr4 = productAttrList.get(3);
		String value4 = prodAtrr4.getValue();
		String[] split4 = value4.split(",");
		
		ProductAttr prodAtrr5 = productAttrList.get(4);
		String value5 = prodAtrr5.getValue();
		String[] split5 = value5.split(",");
		
		for (String string1: split1) {
			for (String string2: split2) {
				for (String string3: split3) {
					for (String string4: split4) {
						for (String string5: split5) {
							StringBuffer buffer = new StringBuffer();
							buffer.append(string1.trim());
							buffer.append(" ");
							buffer.append(string2.trim());
							buffer.append(" ");
							buffer.append(string3.trim());
							buffer.append(" ");
							buffer.append(string4.trim());
							buffer.append(" ");
							buffer.append(string5.trim());
							list.add(buffer.toString().trim());
						}
					}
				}
			}
		}
		
		return list;
	}

}
