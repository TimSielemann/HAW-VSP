package accessor_one;

public abstract class ClassTwoImplBase {
	//Schnittstellenbeschreibung AccessorOne
	public abstract int methodOne(double param1) throws SomeException110;
	//Schnittstellenbeschreibung AccessorOne
	public abstract double methodTwo() throws SomeException112;

	public static ClassTwoImplBase narrowCast(Object rawObjectRef) {
		//Seq.Diagramm: Namensdienstaufruf und Narrow-Objekterzeugung Punkt 4.1
		return new ClassTwoImplBaseStub(rawObjectRef);

	}

}
