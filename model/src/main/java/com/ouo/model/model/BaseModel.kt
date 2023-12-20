package com.ouo.model.model

/**
 * Created by oxq on 2023/12/19.
 */


data class BaseModel<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
)

//在Kotlin中，data关键字用于声明数据类（data class）。
// 数据类是一种特殊的类，它用于存储和传输数据，而不需要太多的样板代码。
// 当你使用data关键字来声明一个类时，编译器会自动生成一些有用的方法，如equals()、hashCode()和toString()等。这使得数据类在处理数据时更加方便和简洁。


//val修饰符表示属性是只读的（不可变的）。一旦属性被赋值后，它的值将不能被修改。
// 这类似于Java中的final关键字。你可以使用val来声明常量或只需在初始化时赋值且不需要再修改的属性。



//var修饰符表示属性是可变的。可以在任何时候对这个属性进行赋值操作。
// 这类似于Java中的普通变量。你可以使用var来声明需要在多个地方修改值的属性。