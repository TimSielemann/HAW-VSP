package accessor_two;

public abstract class ClassOneImplBase {
	//Schnittstellenbeschreibung AccessorTwo
	public abstract double methodOne(String param1, double param2)
			throws SomeException112;
	//Schnittstellenbeschreibung AccessorTwo
	public abstract double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304;

	public static ClassOneImplBase narrowCast(Object rawObjectRef) {
		//Seq.Diagramm: Namensdienstaufruf und Narrow-Objekterzeugung Punkt 4.1
		return new ClassOneImplBaseStub(rawObjectRef);
	}

}
