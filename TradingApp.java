import java.util.*;

class Stock {
    String symbol; double price;
    Stock(String s, double p){ symbol=s; price=p; }
}

class Portfolio {
    Map<String,Integer> holdings = new HashMap<>();
    double cash = 10000;
    void buy(Stock st,int qty){
        double cost=st.price*qty;
        if(cash>=cost){ cash-=cost; holdings.put(st.symbol, holdings.getOrDefault(st.symbol,0)+qty); }
        else System.out.println("Not enough cash!");
    }
    void sell(Stock st,int qty){
        int owned=holdings.getOrDefault(st.symbol,0);
        if(owned>=qty){ cash+=st.price*qty; holdings.put(st.symbol,owned-qty); }
        else System.out.println("Not enough shares!");
    }
    void report(List<Stock> market){
        double total=cash;
        System.out.println("\n--- Portfolio Report ---");
        for(Stock st: market){
            int qty=holdings.getOrDefault(st.symbol,0);
            if(qty>0){
                double val=qty*st.price;
                total+=val;
                System.out.println(st.symbol+": "+qty+" shares worth "+val);
            }
        }
        System.out.println("Cash: "+cash+" | Total Value: "+total);
    }
}

public class TradingApp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        List<Stock> market=Arrays.asList(new Stock("AAPL",150),new Stock("GOOG",2800),new Stock("TSLA",700));
        Portfolio pf=new Portfolio();
        while(true){
            System.out.println("\n1.View Market 2.Buy 3.Sell 4.Report 5.Exit");
            int ch=sc.nextInt();
            if(ch==1) market.forEach(s->System.out.println(s.symbol+" $"+s.price));
            else if(ch==2){ System.out.print("Symbol? "); String sym=sc.next(); Stock st=market.stream().filter(x->x.symbol.equals(sym)).findFirst().orElse(null); if(st!=null){ System.out.print("Qty? "); pf.buy(st,sc.nextInt()); } }
            else if(ch==3){ System.out.print("Symbol? "); String sym=sc.next(); Stock st=market.stream().filter(x->x.symbol.equals(sym)).findFirst().orElse(null); if(st!=null){ System.out.print("Qty? "); pf.sell(st,sc.nextInt()); } }
            else if(ch==4) pf.report(market);
            else break;
        }
    }
}
