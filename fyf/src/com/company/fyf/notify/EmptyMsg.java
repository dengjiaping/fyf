package com.company.fyf.notify;

class EmptyMsg extends BaseMsg<Void>implements IMsg<Void> {
	
	public EmptyMsg(String key) {
		this.key = key ;
	}
	
	@Override
	public void setContent(Void t) {
		throw new IllegalAccessError("Empty msg cannot save content") ;
	}

	@Override
	public Void getContent() {
		return null;
	}

}
