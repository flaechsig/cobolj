package de.cobolj.statement.accept;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.oracle.truffle.api.frame.VirtualFrame;

public class DateTimeInputNode extends InputNode {
	
	/** Für die Ausgabe zu verwendendes Pattern */
	private DateTimePattern pattern;

	public DateTimeInputNode(DateTimePattern pattern) {
		this.pattern = pattern;
	}
	
	public static enum DateTimePattern {
		DATE_YY("yyMMdd"),
		DATE_YYYY("yyyyMMdd"),
		DAY_YY("yyDDD"),
		DAY_YYYY("yyyyDDD"),
		DAY_OF_WEEK("c"), 
		TODAYS_DATE_YY("MMddyy"),
		TODAYS_DATE_YYYY("MMddyyyy"),
		TODAYS_NAME("EEEE"),
		TIME("HHmmssSS"),
		TIMER("N"),
		YEAR("yyyy"),
		YYYYMMDD("yyyyMMdd"),
		YYYYDDD("yyyyDDD");
		
		DateTimePattern(String pattern) {
			this.pattern = pattern;
		}
		
		String pattern = null;
	}

	@Override
	public Object executeGeneric(VirtualFrame frame) {
		String result = DateTimeFormatter.ofPattern(pattern.pattern, Locale.UK).format(LocalDateTime.now());
	
		if(pattern == DateTimePattern.TODAYS_NAME) {
			result = StringUtils.rightPad(result, 9);
		} else if( pattern == DateTimePattern.TIMER) {
			long timer = (long) (Long.parseLong(result)/2.4);
			result = String.valueOf(timer);
		}
		return result;
	}

}
