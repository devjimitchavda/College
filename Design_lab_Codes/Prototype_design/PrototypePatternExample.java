
public class PrototypePatternExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        Vehicle a = new Vehicle();
        a.insertData();

        Vehicle b = (Vehicle) a.clone();
        System.out.println("A" + a.getVehicleList());
        System.out.println("B" + b.getVehicleList());
        List<String> list = b.getVehicleList();
        list.add("Honda New Amaze");

        System.out.println("B" + b.getVehicleList());

        b.getVehicleList().remove("Audi A4");
        System.out.println("B" + list);
        System.out.println("A" + a.getVehicleList());
        b.modify();
    }
}