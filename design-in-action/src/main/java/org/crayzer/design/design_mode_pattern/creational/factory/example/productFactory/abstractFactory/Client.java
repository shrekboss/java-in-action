package org.crayzer.design.design_mode_pattern.creational.factory.example.productFactory.abstractFactory;

/**
 * class_name: Client
 * package: org.crayzer.demo.factory.abstractFactory
 * describe:
 * <p>
 *      优点:
 * 	        抽象工厂模式隔离了具体类的生成，使得客户并不需要知道什么被创建。由于这种隔离，更换一个具体工厂就变得相对容易。
 * 	        所有的具体工厂都实现了抽象工厂中定义的那些公共接口，因此只需改变具体工厂的实例，就可以在某种程度上改变整个软件系统的行为。
 * 	        另外，应用抽象工厂模式可以实现高内聚低耦合的设计目的，因此抽象工厂模式得到了广泛的应用。
 * 	        当一个产品族中的多个对象被设计成一起工作时，它能够保证客户端始终只使用同一个产品族中的对象。
 * 	        这对一些需要根据当前环境来决定其行为的软件系统来说，是一种非常实用的设计模式。
 * 	        增加新的具体工厂和产品族很方便，无须修改已有系统，符合“开闭原则”。
 *
 *      缺点:
 * 	        在添加新的产品对象时，难以扩展抽象工厂来生产新种类的产品，这是因为在抽象工厂角色中规定了所有可能被创建的产品集合，
 * 	        要支持新种类的产品就意味着要对该接口进行扩展，而这将涉及到对抽象工厂角色及其所有子类的修改，显然会带来较大的不便。
 * 	        开闭原则的倾斜性（增加新的工厂和产品族容易，增加新的产品等级结构麻烦）。
 *
 *      备注:
 * 	        产品等级结构：产品等级结构即产品的继承结构，如一个抽象类是电视机，其子类有海尔电视机、海信电视机、TCL电视机，
 * 	                    则抽象电视机与具体品牌的电视机之间构成了一个产品等级结构，抽象电视机是父类，而具体品牌的电视机是其子类。
 *          产品族：在抽象工厂模式中，产品族是指由同一个工厂生产的，位于不同产品等级结构中的一组产品，如海尔电器工厂生产的海尔电
 *                 视机、海尔电冰箱，海尔电视机位于电视机产品等级结构中，海尔电冰箱位于电冰箱产品等级结构中。
 * </p>
 * @author <a href="mailto:crayzer.chen@gmail.com">crayzer</a>
 **/
public class Client {
    public static void main(String[] args) {
        try {
            EFactory factory;
            Television tv;
            AirConditioner ac;
            factory = (EFactory) XMLUtil.getBean();
            tv = factory.produceTelevision();
            tv.play();
            ac = factory.produceAirConditioner();
            ac.changeTemperature();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
