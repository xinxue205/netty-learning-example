package com.sanshengshui.netty;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;

public class Utils {
	static {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run(){
				for (Entry<ChannelHandlerContext, String> e: map.entrySet()) {
					ChannelHandlerContext ctx = e.getKey();
					ctx.write("are you ok, "+ e.getValue() +"?");
			        ctx.flush();
				}
			}
		}, 3000, 5000, TimeUnit.MILLISECONDS);
	}
	
	public static Map<ChannelHandlerContext, String> map = new ConcurrentHashMap<ChannelHandlerContext, String>();

}
