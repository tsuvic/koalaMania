/* Japanese initialisation for the jQuery UI date picker plugin. */
/* Written by Kentaro SATO (kentaro@ranvis.com). */

var holidays;
var holidaysClassName = "date-holiday-text";

$.get("https://holidays-jp.github.io/api/v1/date.json", function(holidaysData){
	holidays = Object.keys(holidaysData);
});

( function( factory ) {
	"use strict";

	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define( [ "../widgets/datepicker" ], factory );
	} else {

		// Browser globals
		factory( jQuery.datepicker );
	}
})(function(datepicker) {
	"use strict";
	
	datepicker.regional.ja = {
		closeText: "閉じる",
		prevText: "&#x3C;前",
		nextText: "次&#x3E;",
		currentText: "今日",
		monthNames: [ "1月", "2月", "3月", "4月", "5月", "6月",
		"7月", "8月", "9月", "10月", "11月", "12月" ],
		monthNamesShort: [ "1月", "2月", "3月", "4月", "5月", "6月",
		"7月", "8月", "9月", "10月", "11月", "12月" ],
		dayNames: [ "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日" ],
		dayNamesShort: [ "日", "月", "火", "水", "木", "金", "土" ],
		dayNamesMin: [ "日", "月", "火", "水", "木", "金", "土" ],
		weekHeader: "週",
		dateFormat: "yy月m月d日",
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: "年",
		showButtonPanel: true, // "今日"ボタン, "閉じる"ボタンを表示する
		changeYear: true, //年を表示
		changeMonth: true, //月を選択
		maxDate: new Date(),
		beforeShowDay: function(date) {
						      if (date.getDay() == 0) {
						        return [true, holidaysClassName , null];
						      } else if (date.getDay() == 6) {
						        return [true, holidaysClassName , null];
						      }
						
						      for (var i = 0; i < holidays.length; i++) {
						        var holiday = new Date(Date.parse(holidays[i]));
						        if (holiday.getYear() == date.getYear() &&
						            holiday.getMonth() == date.getMonth() &&
						            holiday.getDate() == date.getDate()) {
						          return [true, holidaysClassName , null];
						        }
						      }
						
						      return [true, 'day-weekday', null];
						}
	};
		
		
	datepicker.setDefaults( datepicker.regional.ja );
	
	return datepicker.regional.ja;

});