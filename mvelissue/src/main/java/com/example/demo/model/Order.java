package com.example.demo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private OrderHead orderHead;
  private List<OrderList> orderList;
  private int goodsIndex; //current goods index
  
  
}
