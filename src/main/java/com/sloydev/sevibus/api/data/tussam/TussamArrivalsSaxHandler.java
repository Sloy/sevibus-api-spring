package com.sloydev.sevibus.api.data.tussam;


import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class TussamArrivalsSaxHandler extends DefaultHandler {

	private Bus tmpBus, bus1, bus2;
	private Builder build = Builder.NO;


	public void clear() {
        tmpBus = null;
        bus1 = null;
        bus2 = null;
    }

    private enum Builder {
		NO, TIEMPO, DISTANCIA, RUTA
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(!Builder.NO.equals(build)){
			StringBuilder sb = new StringBuilder();
			sb.append(ch, start, length);
			switch (build){
				case TIEMPO:
					tmpBus.time = Integer.parseInt(sb.toString());
					break;
				case DISTANCIA:
					tmpBus.distance = Integer.parseInt(sb.toString());
					break;
				default:
					break;
			}
			sb.setLength(0);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("e1")){
			tmpBus = new Bus();
		}else if(qName.equals("e2")){
			tmpBus = new Bus();
		}else if(qName.equals("minutos")){
			build = Builder.TIEMPO;
		}else if(qName.equals("metros")){
			build = Builder.DISTANCIA;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("e1")){
			bus1 = tmpBus;
			tmpBus = null;
		}else if(qName.equals("e2")){
			bus2 = tmpBus;
			tmpBus = null;
		}else if(qName.equals("minutos") || qName.equals("metros")){
			build = Builder.NO;
		}
	}

    public TwoBuses getResult() {
		return new TwoBuses(bus1, bus2);
    }

    class Bus {
        int time;
        int distance;
    }

    public static class TwoBuses {
        Bus bus1;
        Bus bus2;

        private TwoBuses(Bus bus1, Bus bus2) {
            this.bus1 = bus1;
            this.bus2 = bus2;
        }
    }
}
