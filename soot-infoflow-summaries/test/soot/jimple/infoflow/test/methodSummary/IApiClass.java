package soot.jimple.infoflow.test.methodSummary;

public interface IApiClass {
    public Data getNonPrimitiveVariable();

    public int standardFlow(int i);

    public Object standardFlow(Object i);

    public int standardFlow2(int i, int j);

    public int standardFlow3(Data data);

    public Data standardFlow4(int i, Object o);

    public int noFlow(int i);

    public Data noFlow3(Data data);

    public Data noFlow4(int i, Object o);

    public int paraToVar(int i, int j);

    public Data paraToVar2(int i, Object o);

    public Data paraToVarX(int i, Object o);

    public void paraToVarY(int i, Object o);

    public int paraToStaticVar1(int i, int j);

    public Data paraToStaticVar2(int i, Object o);

    public void paraToparaFlow1(int i, Data o);

    public void paraToparaFlow2(int i, Object o, Data data);

    public void paraToparaFlow3(int i, Object o, Data data, Data data2);

    public Data mixedFlow1(int i, Data data);

    public int intParaToReturn();

    public int intInDataToReturn();

    public int intInDataToReturn2();

    public int intInDataToReturn3();

    public Data dataFieldToReturn();

    public Object objInDataFieldToReturn();

    public Data dataFieldToReturn2();

    public Data getNonPrimitive2Variable();

    public void swap();

    public void data1ToDate2();

    public void fieldToPara(Data d);

    public String transferStringThroughDataClass(IGapClass gap, String in);

    public String transferNoStringThroughDataClass(IGapClass gap, String in);

    public String storeStringInGapClass(IGapClass gap, String in);

    public String storeAliasInGapClass(IGapClass gap, String in);

    public String storeAliasInGapClass2(IGapClass gap, String in);

    public String gapToGap(IUserCodeClass gap, String in);

    public String callToCall(IGapClass gap, String in);
}