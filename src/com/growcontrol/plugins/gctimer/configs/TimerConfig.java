package com.growcontrol.plugins.gctimer.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.growcontrol.plugins.gctimer.PluginDefines;
import com.poixson.commonapp.config.xConfig;
import com.poixson.commonjava.Utils.utils;
import com.poixson.commonjava.Utils.utilsObject;
import com.poixson.commonjava.Utils.xHashable;
import com.poixson.commonjava.xLogger.xLog;


public class TimerConfig implements xHashable {
	private static final String LOG_NAME = "CONFIG";

	public final String key;

	public final boolean enabled;



	// configs from set
	public static Map<String, TimerConfig> get(final Set<Object> dataset) {
		if(utils.isEmpty(dataset))
			return null;
		final Map<String, TimerConfig> configs = new HashMap<String, TimerConfig>();
		for(final Object obj : dataset) {
			try {
				final Map<String, Object> datamap =
						utilsObject.castMap(
								String.class,
								Object.class,
								obj
						);
				final TimerConfig cfg = get(datamap);
				configs.put(cfg.getKey(), cfg);
			} catch (Exception e) {
xLog.getRoot(LOG_NAME).trace(e);
			}
		}
		return configs;
	}
	// config from map
	public static TimerConfig get(final Map<String, Object> datamap) {
		if(utils.isEmpty(datamap))
			return null;
		final xConfig config = new xConfig(datamap);
		// default to enabled if key doesn't exist
		final boolean enabled =
				config.exists(PluginDefines.CONFIG_TIMER_ENABLED)
				? config.getBool(PluginDefines.CONFIG_TIMER_ENABLED, false)
				: true;
		return new TimerConfig(
				enabled
		);
	}



	// new config instance
	public TimerConfig(final boolean enabled) {
		this.enabled = enabled;
		this.key = this.genKey();
	}



	@Override
	public String toString() {
		return this.key;
	}
	@Override
	public String getKey() {
		return this.key;
	}
	private String genKey() {
		final StringBuilder str = new StringBuilder();
//		final String host = this.getHost();
//		str.append(host == null ? "*" : host.toLowerCase());
//		str.append(":").append(this.port);
//		if(this.ssl) str.append("<ssl>");
		return str.toString();
	}
	@Override
	public boolean matches(final xHashable hashable) {
		if(hashable == null || !(hashable instanceof TimerConfig) )
			return false;
		final TimerConfig config = (TimerConfig) hashable;
		if(this.enabled != config.enabled)
			return false;
		return this.getKey().equalsIgnoreCase(config.getKey());
	}



	// logger
	private volatile xLog _log = null;
	public xLog log() {
		if(this._log == null)
			this._log = xLog.getRoot(LOG_NAME);
		return this._log;
	}



}
