package reva.revaDsl.impl;

public class XVariableDeclarationImplCustom extends XVariableDeclarationImpl {
	
	@Override
	public String getIdentifier() {
		return name;
	}
	
	@Override
	public String getSimpleName() {
		return name;
	}
	
	@Override
	public String getQualifiedName() {
		return name;
	}
	
	@Override
	public String getQualifiedName(char innerClassDelimiter) {
		return name;
	}
	
	@Override
	public String toString() {
		if (isWriteable()) {
			return "var " + getSimpleName(); 
		}
		return "val " + getSimpleName();
	}
}
