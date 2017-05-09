intsdfsd = '123 '   
print (intsdfsd)
stre = 'imooc'  
print (stre+intsdfsd)
print ("Bob said I'm OK\n")
print ('Bob said I\'m OK\n')
print ('Bob said \"I\'m OK\".\n')
print (r'...slfdjsdfs/s/n\n')
print (r'''Python is created by "Guido".
It is free and easy to learn.
Let's start learn Python in imooc!''')
print (u'中文')
print ("静夜思\n床前明月光，\n疑是地上霜。\n举头望明月，\n低头思故乡.")
a=1+2+3.3
print (a);
a=4%2
b=11/4
print (a)
print (b)

#a=True
#if(a==True){
#print ("1"){
#}else{
#print ("2"){
#}
#print (not a)
a="python"
b=1
print ('hello,',b or "world")

#因为Python把0、空字符串''和None看成 False，其他数值和非空字符串都看成 True

classmatea=["a",123,False]
print (classmatea)
print (classmatea[-1])
print (classmatea[-2])
classmatea.append("666")
print (classmatea)
classmatea.insert(0,"111");
print (classmatea)

#classmatea.del(0);
print (classmatea.pop(1));
print (classmatea);

classmatea[1]=333;
print (classmatea);

ttemp=("222",111,True)
print (ttemp);
#ttemp[0]="111";#tuple是另一种有序的列表不能修改删除增加
print (ttemp[0]);

ttem=(1)
print (ttem);
ttem=(1,)#单元素用","表示
print (ttem[0]);

#动态改变tuple的值,需要添加list
tupletest=("a","b",[111,True])
listtest=tupletest[2];
listtest[0]=333;
listtest[1]=False;
#tupletest[0]="B";不是list元素的还是不能做更改
print (tupletest);


age=20;
if age>18:
	print ("age is ",age)
else:
	print ("age not",18)
print ("end");


num=6;
if num>9:
	print ("num>9")
elif num>7:
	print ("num>7")
else:
	print ("num is",num);
	
	
#while循环的用法
a=10;
b=0;
while b<=a:
	print (b);
	if b>3:
		break;
	b=b+1;
	
	
#for循环的用法
listtemp=[111,222,333,444];
for value in listtemp:
	print (value);
	
	
#for多层循环
listtemp=["a","b","c"];
listtemp1=["1","2","3"];
for x in listtemp:
	for y in listtemp1:
		print (x+y);

#跟java中map集合一样的dict集合
d={"a":"张三","b":"李四","c":"王五"}
if "a" in d:
	print (d["a"]);
	print (d.get("b"))#两种方法都可以
	

#dict和list区别:dict的查找速度快不是没有代价的，dict的缺点是占用内存大，还会浪费很多内容，list正好相反，占用内存小，但是查找速度慢。

#dict添加元素
d={"a":"张三","b":"李四","c":"王五"}
d["d"]="赵六"
d["e"]="陈七"
print (d);

#dict遍历元素
for key in d:
	print (key,d.get(key));

	
	
	
	
	
	
	