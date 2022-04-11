package com.nic.control;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import jpcap.packet.*;


public class PacketAnalyze {
	static Packet packet;
	static HashMap<String,String> att,att1;
	public PacketAnalyze(Packet packet){
		this.packet = packet;	
	}
	public static HashMap<String,String> packetClass(){
		att1 = new HashMap<String,String>();
		if(packet.getClass().equals(ICMPPacket.class)){
			att1 = ICMPanalyze();
		}else if(packet.getClass().equals(TCPPacket.class)){
			att1 = TCPanalyze();
		}else if(packet.getClass().equals(UDPPacket.class)){
			att1 = UDPanalyze();
		}
		return att;
	}
	public static HashMap<String,String> IPanalyze(){
		att = new HashMap<String,String>();
		if(packet instanceof IPPacket){
			IPPacket ippacket = (IPPacket) packet;
			att.put("Э��", new String("IP"));
			att.put("ԴIP", ippacket.src_ip.toString().substring(1, ippacket.src_ip.toString().length()));
			att.put("Ŀ��IP", ippacket.dst_ip.toString().substring(1, ippacket.dst_ip.toString().length()));
			att.put("TTL", String.valueOf(ippacket.hop_limit));
			att.put("ͷ����", String.valueOf(ippacket.header.length));
			att.put("�Ƿ���������Ƭ", String.valueOf(ippacket.more_frag));
		}
		return att;
	}
	public static HashMap<String,String> ICMPanalyze(){
		att = new HashMap<String,String>();
		ICMPPacket icmppacket = (ICMPPacket) packet;
		att.put("Э��", new String("ICMP"));
		att.put("ԴIP", icmppacket.src_ip.toString().substring(1, icmppacket.src_ip.toString().length()));
		att.put("Ŀ��IP", icmppacket.dst_ip.toString().substring(1, icmppacket.dst_ip.toString().length()));
		return att;
	}
	public static HashMap<String,String> TCPanalyze(){
		att = new HashMap<String,String>();
		TCPPacket tcppacket = (TCPPacket) packet;
		EthernetPacket ethernetPacket=(EthernetPacket)packet.datalink;
		att.put("Э��", new String("TCP"));
		att.put("ԴIP", tcppacket.src_ip.toString().substring(1, tcppacket.src_ip.toString().length()));
		att.put("Դ�˿�", String.valueOf(tcppacket.src_port));
		att.put("Ŀ��IP", tcppacket.dst_ip.toString().substring(1, tcppacket.dst_ip.toString().length()));
		att.put("Ŀ�Ķ˿�", String.valueOf(tcppacket.dst_port));
		att.put("ԴMAC", ethernetPacket.getSourceAddress());
		att.put("Ŀ��MAC", ethernetPacket.getDestinationAddress());
		try {
			att.put("����", new String(tcppacket.data,"gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return att;
	}
	public static HashMap<String,String> UDPanalyze(){
		att = new HashMap<String,String>();
		UDPPacket udpppacket = (UDPPacket) packet;
		EthernetPacket ethernetPacket=(EthernetPacket)packet.datalink;
		att.put("Э��", new String("UDP"));
		att.put("ԴIP", udpppacket.src_ip.toString().substring(1, udpppacket.src_ip.toString().length()));
		att.put("Դ�˿�", String.valueOf(udpppacket.src_port));
		att.put("Ŀ��IP", udpppacket.dst_ip.toString().substring(1, udpppacket.dst_ip.toString().length()));
		att.put("Ŀ�Ķ˿�", String.valueOf(udpppacket.dst_port));
		att.put("ԴMAC", ethernetPacket.getSourceAddress());
		att.put("Ŀ��MAC", ethernetPacket.getDestinationAddress());
		try {
			att.put("����", new String(udpppacket.data,"gbk"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		return att;
	}
}