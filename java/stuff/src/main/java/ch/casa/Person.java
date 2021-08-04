package ch.casa;

public class Person {
    final String name;
    final Sex sex;
    final int age;
    final String country;

    public Person(String name, Sex sex, int age, String country) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", sex='" + sex + '\'' +
            ", age=" + age +
            ", country='" + country + '\'' +
            '}';
    }

    enum Sex {
        MALE,
        FEMALE
    }
}
