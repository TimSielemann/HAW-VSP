package accessor_one;

public abstract class ClassOneImplBase {
	//Schnittstellenbeschreibung AccessorOne
	public abstract String methodOne(String param1, int param2)
			throws SomeException112;

	public static ClassOneImplBase narrowCast(Object rawObjectRef) {
		//Seq.Diagramm: Namensdienstaufruf und Narrow-Objekterzeugung Punkt 4.1
		return new ClassOneImplBaseStub(rawObjectRef);
	}

}
