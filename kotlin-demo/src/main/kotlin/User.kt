class User {
    var name: String? = null;
    var age: Int = 0;
    var sex: String? = null;

    override fun toString(): String {
        return "name: $name, age: $age, sex: $sex";
    }
}

fun main(args: Array<String>) {
    val user = User();
    user.name = "zhangsan";
    user.age = 20
    user.sex = "男";
    println(user);
    println(user.toString());
    println(user.hashCode());
}