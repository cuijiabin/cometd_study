(function(j) {
	function p(a) {
        return jQuery.GC(t, "." + a)[0]
    }
    function x() {
        j.DatePicker.hide()
    }
    function y() {
        top.j.DatePicker.hide()
    }
    function z(a) {
        jQuery.E(a).stop()
    }
	var t = j('<div class="date_picker" onclick="jQuery.DatePicker.closeSelect();"><div class="wrap"></div><div class="time"><span class="timeTip"></span><span class="timeWrap"><input type="text" maxlength="2" class="hInput" onfocus="this.select();jQuery.DatePicker.currentTimeSet = this;" onblur="jQuery.DatePicker.selectedDate.setHours(this.value);" />:<input type="text" maxlength="2" class="mInput" onfocus="this.select();jQuery.DatePicker.currentTimeSet = this;" onchange="jQuery.DatePicker.selectedDate.setMinutes(this.value);"/>:<input type="text" maxlength="2" class="sInput" onfocus="this.select();jQuery.DatePicker.currentTimeSet = this;" onblur="jQuery.DatePicker.selectedDate.setSeconds(this.value);"/><button class="timeUp" onmousedown="jQuery.DatePicker.setTime(\'up\',\'auto\')" onmouseup="jQuery.DatePicker.setTime(\'up\',\'no\')" onclick="jQuery.DatePicker.setTime(\'up\');"></button><button class="timeDown" onmousedown="jQuery.DatePicker.setTime(\'down\',\'auto\')" onmouseup="jQuery.DatePicker.setTime(\'down\',\'no\')" onclick="jQuery.DatePicker.setTime(\'down\');"></button></span><input type="button" class="timeConfirm" onclick="jQuery.DatePicker.confirm();" /></div><ul class="list"></ul>' + (j.browser.version=="6.0" ? '<iframe src="javascript:false"></iframe>': "") + "</div>")[0],
	A;
	j(t).bind("click",z);
	j.DatePicker={
		body: t,
        wrap: p("wrap"),
        time: p("time"),
        list: p("list"),
        timeTip: p("timeTip"),
        timeConfirm: p("timeConfirm"),
        hInput: p("hInput"),
        mInput: p("mInput"),
        sInput: p("sInput"),
        yInput: function() {
            return jQuery.GC(this.wrap, ".yearInput")[0].firstChild
        },
        MInput: function() {
            return jQuery.GC(this.wrap, ".monthInput")[0].firstChild
        },
        iframe: t.getElementsByTagName("iframe")[0],
        target: 0,
        format: 0,
        display: 0,
        lang: 0,
        weekStartDay: 1,
        closeSelectTarget: 0,
        disabledWeeks: [],
        disabledWeekDays: [],
        disabledDays: [],
        currentDate: 0,
        currentWeek: 0,
        currentTimeSet: 0,
        selectedDate: 0,
        selectedWeek: 0,
        selectWeek: 0,
        showWeek: 0,
        tmpDate: 0,
        call: null,
        date: {
            month: {
                en: ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
                en_US: ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
                zh_CN: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                zh_TW: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
            },
            week: {
                en: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Wk"],
                en_US: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Wk"],
                zh_CN: ["日", "一", "二", "三", "四", "五", "六", "周"],
                zh_TW: ["日", "一", "二", "三", "四", "五", "六", "\ufffd"]
            },
            time: {
                en: "Time",
                en_US: "Time",
                zh_CN: "时间",
                zh_TW: "\ufffd\ufffd"
            },
            confirm: {
                en: "Confirm",
                en_US: "Confirm",
                zh_CN: "确 定",
                zh_TW: "\ufffd 定"
            },
            today: {
                en: "Today",
                en_US: "Today",
                zh_CN: "今 天",
                zh_TW: "今 天"
            },
            quick: {
                en: "Quick Selection",
                en_US: "Quick Selection",
                zh_CN: "快速选择",
                zh_TW: "快速\ufffd\ufffd"
            }
        },
		show:function(a,b){
			var d = jQuery.E(a);
            d.stop();
            if (top.j && top.j.DatePicker && !b.self && top != self) {
                j(document.documentElement).click(y);
                j(window).unload(y);
                top.j.DatePicker.window = window;
                top.j.DatePicker.top = true;
                top.j.DatePicker.show(a, b);
                return false
            }
            if (b.target) this.target = b.target;
            else {
                this.target = d.target;
                this.target.select && this.target.select()
            }
            j(this.target).bind("click",z);
            this.lang = b.lang ? b.lang: "zh_CN";
            this.call = b.call ? b.call: null;
            this.weekStartDay = b.weekStartDay ? b.weekStartDay: 1;
            this.showWeek = b.showWeek ? b.showWeek: false;
            this.selectWeek = b.selectWeek ? (this.showWeek = true) : false;
            this.format = b.format ? b.format: "yyyy-MM-dd";
            this.timeConfirm.value = this.date.confirm[this.lang];
            this.disabledWeekDays = [];
            this.disabledDays = [];
            this.disabledWeeks = [];
            var f = new Array(7),
            e = [],
            i = [];
            b.disabledWeekDays && j.each(b.disabledWeekDays, 
            function(a,m) {
                f[m - 1] = 1
            });
            b.disabledWeeks && j.each(b.disabledWeeks, 
            function(a,m) {
                i[m - 1] = 1
            });
            b.disabledDays && j.each(b.disabledDays, 
            function(a,m) {
                e[m - 1] = 1
            });
            this.disabledWeeks = i;
            this.disabledWeekDays = f.concat(f).slice(this.weekStartDay - 1, this.weekStartDay + 6);
            this.disabledDays = e;
            this.startDay = b.startDay ? jQuery.parseDate(b.startDay, this.format).getTime() || 0: 0;
            this.endDay = b.endDay ? jQuery.parseDate(b.endDay, this.format).getTime() || Infinity: Infinity;
            this.currentWeek = false;
            if (this.target.value) {
                this.currentDate = jQuery.parseDate(this.target.value, this.format);
                if (this.target.value.hasString(" - ")) this.currentWeek = true
            } else if (jQuery.isArray(this.target)) {
                var k = [];
                jQuery.each(this.target, 
                function(m) {
                    k.push("INPUT,TEXTAREA".hasString(m.nodeName) ? m.value: m.innerHTML)
                });
                this.currentDate = k[0] ? jQuery.parseDate(k.join(","), this.format) : null
            } else this.currentDate = null;
            if (!jQuery.isDate(this.currentDate)) this.currentDate = null;
            this.selectedDate = jQuery.cloneDate(this.currentDate);
            this.fill();
            if (!this.currentTimeSet) this.currentTimeSet = this.mInput;
            this.closeSelectTarget = null;
            j(document.documentElement).click(x);
            if (!A) {
                document.body.appendChild(this.body);
                A = 1
            }
            a = jQuery.getX(d.target);
            b = jQuery.getY(d.target) + jQuery.height(d.target);
            jQuery.scrollX(d.target);
            jQuery.scrollY(d.target);
            if (this.top) {
                a += jQuery.frameX(this.window) - this.window.jQuery.scrollX(d.target);
                b += jQuery.frameY(this.window) - this.window.jQuery.scrollY(d.target);
                this.top = false
            }
            this.body.style.cssText = "display:block;top:" + b + "px;left:" + a + "px;";
            b = jQuery.windowHeight();
            a = jQuery.height(this.body);
            var c = jQuery.height(d.target),
            h = jQuery.windowWidth(),
            l = jQuery.width(this.body);
            d = jQuery.width(d.target);
            if (jQuery.getY(this.body) + a > b + jQuery.scrollY()) this.body.style.marginTop = "-" + (a + c + 1) + "px";
            if (jQuery.getX(this.body) + l > h + jQuery.scrollX()) this.body.style.marginLeft = "-" + (l - d) + "px";
            d = jQuery.getX(this.body);
            b = jQuery.getY(this.body);
            if (b < 0) this.body.style.marginTop = parseInt(this.body.style.marginTop) - b + "px";
            if (d < 0) this.body.style.marginLeft = parseInt(this.body.style.marginLeft) - d + "px";
            if (this.iframe) this.iframe.style.cssText = "width:" + jQuery.width(this.body) + "px;height:" + a + "px;";
            this.display = true
		},
		hide: function() {
            if (this.display) {
                this.display = false;
                j(this.body).hide();
                j(document.documentElement).unbind("click",x);
            }
        },
        selectMonthY: function(a, b) {
            a = jQuery.E(a);
            a.stop();
            if (b == "prev" || b == "next") {
                b = b == "prev" ? -10: 10;
                a = jQuery.GC(this.wrap, ".yearCont")[0];
                a = a.getElementsByTagName("li");
                for (var d = 0; d < 10; d++) a[d].innerHTML = Number(a[d].innerHTML) + b
            } else {
                a = a.target;
                if (this.closeSelectTarget) {
                    this.closeSelect();
                    return false
                }
                j(a.parentNode).addClass("on");
                this.closeSelectTarget = a.nextSibling;
                if (!b) a.value = a.getAttribute("rel");
                a.select()
            }
        },
        closeSelect: function() {
            var a = this.closeSelectTarget;
            if (a) {
                var b = a.parentNode;
                j(b).removeClass("on");
                a = a.previousSibling.value;
                if (jQuery.trim(b.className) == "monthInput") {
                    if (a > 12) a = 12;
                    if (a < 0) a = 0;
                    this.fill(this.yInput().value, a)
                } else this.fill(a, this.MInput().getAttribute("rel"))
            }
            this.closeSelectTarget = null
        },
        daySelected: function(a, b, d, f) {
            if (arguments.length == 1) {
                var e = a.split(",");
                a = e[0];
                b = e[1];
                d = e[2];
                f = e[3]
            }
            this.selectedDate.setFullYear(a);
            this.selectedDate.setMonth(b - 1);
            this.selectedDate.setDate(d);
            if (f == -1) f = (a % 4 == 0 && a % 100 != 0 || a % 400 == 0) && (new Date(a, 0, 1)).getDay() == 0 ? 54: 53;
            if (f == 0) {
                e = (new Date(a, 0, 1)).getDay();
                if (e == 0) e = 7;
                f = Math.ceil((d - (e != 1 ? 7 - e + 1: 0)) / 7) + (e != 1 ? 1: 0)
            }
            this.selectedWeek = f;
            this.fillTarget();
            this.call && this.call(this.selectedDate)
        },
        weekSelected: function(a) {
            function b(e, i) {
                e = jQuery.cloneDate(e);
                e.setYear(i[0]);
                e.setMonth(i[1] - 1);
                e.setDate(i[2]);
                return e
            }
            a = jQuery.E(a).target;
            var d = a.parentNode.lastChild,
            f = a.nextSibling.getAttribute("rel").split(",");
            d = d.getAttribute("rel").split(",");
            this.selectedWeek = a.innerHTML;
            this.target.value = this.formatWeek(jQuery.formatDate(b(this.selectedDate, f), this.format)) + " - " + this.formatWeek(jQuery.formatDate(b(this.selectedDate, d), this.format));
            this.hide()
        },
        formatWeek: function(a) {
            if (!this.format.hasString("W")) return a;
            var b = "W",
            d;
            if (this.format.hasString("WW")) {
                b = "WW";
                d = jQuery.addZero(this.selectedWeek)
            }
            return a.replace(b, d)
        },
        confirm: function(a) {
            if (a == "today") {
                this.selectedDate = new Date;
                this.fillTarget()
            } else this.daySelected(this.yInput().value, this.MInput().getAttribute("rel"), this.currentDate.getDate())
        },
        setTime: function(a, b) {
            if (b == "auto") this.delay = setTimeout("jQuery.DatePicker.delay = setInterval(\"jQuery.DatePicker.setTime('" + a + "')\",50);", 100);
            else if (b == "no") {
                clearTimeout(this.delay);
                clearInterval(this.delay)
            } else {
                this.closeSelectTarget = null;
                if (this.currentTimeSet.getAttribute("disabled")) this.currentTimeSet = this.hInput;
                var d = this.currentTimeSet.value;
                b = this.currentTimeSet.className;
                var f;
                if (b == "mInput" || b == "sInput") f = 59;
                if (b == "hInput") f = 23;
                a = a == "up" ? Number(d) + 1: Number(d) - 1;
                if (a < 0) a = f;
                if (a > f) a = 0;
                switch (b) {
                case "hInput":
                    this.selectedDate.setHours(a);
                    break;
                case "mInput":
                    this.selectedDate.setMinutes(a);
                    break;
                case "sInput":
                    this.selectedDate.setSeconds(a);
                    break
                }
                this.format.hasString("d") || this.fill(this.selectedDate.getFullYear(), this.selectedDate.getMonth() + 1);
                this.currentTimeSet.value = jQuery.addZero(a);
                this.currentTimeSet.select()
            }
        },
		fill: function(a, b) {
            j(this.body).show();
            var d = new Date,
            f = [];
            if (!this.currentDate) {
                this.currentDate = d;
                this.selectedDate = jQuery.cloneDate(this.currentDate)
            }
            if (!this.selectedDate) this.selectedDate = jQuery.cloneDate(this.currentDate);
            var e = a ? Number(a) : this.currentDate.getFullYear();
            a = b ? Number(b) : this.currentDate.getMonth() + 1;
            this.selectedDate.setFullYear(e);
            this.selectedDate.setMonth(a - 1);
            this.tmpDate = jQuery.cloneDate(this.selectedDate);
            b = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
            if (e % 4 == 0 && e % 100 != 0 || e % 400 == 0) b[2] = 29;
            d = e;
            var i = a,
            k = 1,
            c = (new Date(e, a - 1, 1)).getDay(),
            h = 0;
            h = c >= this.weekStartDay ? c - this.weekStartDay: 7 - this.weekStartDay + c;
            if (h > 0) {
                i--;
                if (i < 1) {
                    i = 12;
                    d--
                }
                k = b[i] - h + 1
            }
            h = a + 1;
            c = e;
            if (h > 12) {
                h = 1;
                c++
            }
            var l = a - 1;
            c = e;
            if (l < 1) {
                l = 12;
                c--
            }
            var m = [],
            n = [];
            j.each(this.date.month[this.lang], 
            function(q, B) {
                var v = q;
                if (q > 5) v = (q - 6) * 2 + 1;
                if (q < 6) if (q != 0) v = q * 2;
                m[v] = '<li onmouseover="this.className=\'on\'" onmouseout="this.className=\'\'" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(' + e + "," + (q + 1) + ');">' + B + "</li>"
            });
            for (c = 0; c < 10; c++) {
                var g = c;
                if (c > 4) g = (c - 5) * 2 + 1;
                if (c < 5) if (c != 0) g = c * 2;
                n[9 - g] = '<li onmouseover="this.className=\'on\'" onmouseout="this.className=\'\'" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(this.innerHTML,' + a + ');">' + (e - c + 4) + "</li>"
            }
            n[10] = '<li class="yearContTolls"><a href="javascript:void(0)" onclick="jQuery.DatePicker.selectMonthY(event,\'prev\');" onfocus="this.blur()">←</a><a href="javascript:void(0)">×</a><a href="javascript:void(0)" onclick="jQuery.DatePicker.selectMonthY(event,\'next\');" onfocus="this.blur()">→</a></li>';
            f.push('<div class="title"><a href="javascript:void(0)" onfocus="this.blur()" class="prev_year" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(' + (e - 1) + "," + a + ');"></a><a href="javascript:void(0)" onfocus="this.blur()" class="prev" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(' + (l == 12 ? e - 1: e) + "," + l + ');"></a><span class="monthInput"><input onclick="jQuery.DatePicker.selectMonthY(event);" maxlength="2" type="text" rel="' + a + '" value="' + this.date.month[this.lang][a - 1] + '" /><ul class="monthCont">' + m.join("") + '</ul></span><span class="yearInput"><input onclick="jQuery.DatePicker.selectMonthY(event,\'year\');" maxlength="4" type="text" value="' + e + '" /><ul class="yearCont">' + n.join("") + '</ul></span><a href="javascript:void(0)" onfocus="this.blur()" class="next" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(' + (h == 1 ? e + 1: e) + "," + h + ');"></a><a href="javascript:void(0)" onfocus="this.blur()" class="next_year" onclick="jQuery.DatePicker.closeSelectTarget = null;jQuery.DatePicker.fill(' + (e + 1) + "," + a + ');"></a></div>');
            f.push('<div class="data"><table>');
            f.push('<tr class="weekHeader">');
            g = this.selectedDate.getMonth() + 1;
            l = this.selectedDate.getFullYear();
            for (c = h = 1; c < g; c++) h += b[c];
            c = (new Date(l, 0, 1)).getDay();
            if (c == 0) c = 7;
            c = Math.ceil((h - (c != 1 ? 7 - c + 1: 0)) / 7) + (c != 1 ? 1: 0);
            this.showWeek && f.push('<td class="week">' + this.date.week[this.lang][7] + "</td>");
            for (h = 0; h < 7; h++) f.push("<td><span>" + this.date.week[this.lang][(this.weekStartDay + h) % 7] + "</td>");
            f.push("</tr>");
            h = 1;
            for (var jj; h < 7; h++) {
                f.push("<tr>");
                if (this.showWeek) f.push('<td class="week ' + (this.selectWeek ? "": " off") + '" onclick="' + (this.selectWeek ? "jQuery.DatePicker.weekSelected(event,'" + c + "')": "") + '">' + c + "</td>");
                if (this.format.hasString("d")) for (g = 1; g < 8; g++) {
                    l = "";
                    n = jQuery.parseDate(d + "-" + i + "-" + k, "yyyy-MM-dd");
                    l = i == this.currentDate.getMonth() + 1 && k == this.currentDate.getDate() && d == this.currentDate.getFullYear() ? "currentDate": i == a ? "currentMonthDate": "otherMonthDate";
                    if (this.disabledWeekDays[g - 1] || this.disabledDays[k - 1] || this.disabledWeeks[h] || (g == 1 ? this.disabledWeekDays[7] : false) || n.getTime() < this.startDay || n.getTime() > this.endDay) {
                        f.push('<td class="' + l + ' disabled" rel="' + d + "," + i + "," + k + '">' + k + "</td>");
                        jj && jj != 7 && jj--
                    } else {
                        var o = k;
                        n = i;
                        var r = d;
                        if (this.currentWeek) {
                            if (l == "currentDate") jj = 7;
                            if (jj) {
                                l += " currentDate";
                                jj--
                            }
                        }
                        if (this.displayType == "week-end") {
                            o = new Date(r, n - 1, o, 0, 0, 0, 0);
                            o.setDate(o.getDate() + (7 - g));
                            r = o.getYear();
                            if (r < 1E3) r += 1900;
                            n = o.getMonth() + 1;
                            o = o.getDate()
                        }
                        var w = c;
                        if (h < 2 && n == 12 && o > 9) w = -1;
                        else if (h > 4 && n == 1 && o < 15) w = 0;
                        var u = Math.abs(this.weekStartDay - 7) + 1,
                        C = u > 1 ? u - 1: u + 6;
                        f.push('<td class="' + l + (g == u || g == C ? " week_end": "") + '" onmouseover="jQuery(this).addClass(\'hover\');" onmouseout="jQuery(this).removeClass(\'hover\');" onclick="jQuery.DatePicker.daySelected(this.getAttribute(\'rel\'));" rel="' + r + "," + n + "," + o + "," + w + '">' + k + "</td>")
                    }
                    k++;
                    if (k > b[i]) {
                        k = 1;
                        i++
                    }
                    if (i > 12) {
                        i = 1;
                        d++
                    }
                } else f.push("<td>N</td>");
                f.push("</tr>");
                c++
            }
            f.push("</table></div>");
            jj = ["y", "M", "d", "h", "m", "s"];
            for (c = 0; c < 6; c++) {
                a = s = jj[c];
                if (jj[c] == "m") s = "mi";
                this.format.hasString(a) ? j(this.body).removeClass("notHas_" + s) : j(this.body).addClass("notHas_" + s)
            }
            if (this.body.className.hasString(["notHas_h", "notHas_mi", "notHas_s"])) {
                j(this.time).addClass("btn");
                j(this.time).hide();
            } else {
                this.hInput.value = jQuery.addZero(this.selectedDate.getHours());
                this.mInput.value = jQuery.addZero(this.selectedDate.getMinutes());
                this.sInput.value = jQuery.addZero(this.selectedDate.getSeconds());
                for (c = 3; c < 6; c++) {
                    a = jj[c];
                    this.format.hasString(a) ? this[a + "Input"].removeAttribute("disabled") : this[a + "Input"].setAttribute("disabled", "disabled")
                }
                this.timeTip.innerHTML = this.date.time[this.lang];
                j(this.time).removeClass("btn");
                jQuery(this.time).show();
            }
            this.wrap.innerHTML = f.join("");
            if (jQuery.hasClass(this.body, "notHas_d")) {
                f = [];
                f.push('<li class="title">' + this.date.quick[this.lang] + "</li>");
                for (c = 0; c < 5; c++) {
                    jj = this.tmpDate;
                    if (this.format.hasString("s")) {
                        g = (c + 1) * 10;
                        jj.setSeconds(g);
                        a = "setSeconds(" + g + ")"
                    } else if (this.format.hasString("m")) {
                        g = (c + 1) * 10;
                        jj.setMinutes(g);
                        a = "setMinutes(" + g + ")"
                    } else if (this.format.hasString("h")) {
                        g = c * 5;
                        jj.setHours(g);
                        a = "setHours(" + g + ")"
                    } else {
                        if (this.format.hasString("M")) {
                            g = this.selectedDate.getMonth() + c;
                            if (g > 12) g -= 12;
                            jj.setMonth(g);
                            a = "setMonth(" + g + ")"
                        } else {
                            g = this.selectedDate.getFullYear() + c;
                            jj.setFullYear(g);
                            a = "setFullYear(" + g + ")"
                        }
                        jQuery(this.time).show();
                    }
                    f.push('<li onmouseover="this.className=\'on\'" onmouseout="this.className=\'\'" onclick="jQuery.DatePicker.selectedDate.' + a + ';jQuery.DatePicker.fillTarget();">' + jQuery.formatDate(jj, this.format) + "</li>")
                }
                this.list.innerHTML = f.join("");
                j(this.list).show();
            } else j(this.list).hide();
        },
        fillTarget: function() {
            var a = this.formatWeek(jQuery.formatDate(this.selectedDate, this.format));
            if (jQuery.isArray(this.target)) {
                a = a.replace(/\D/g, ",").split(",");
                jQuery.each(this.target, 
                function(d, b) {
                    if ("INPUT,TEXTAREA".hasString(b.nodeName)) b.value = a[d];
                    else b.innerHTML = a[d]
                })
            } else if ("INPUT,TEXTAREA".hasString(this.target.nodeName)) this.target.value = a;
            else this.target.innerHTML = a;
            this.hide()
        },
        onOpen: function(a, b, d, f, e) {
            e.target = b;
            e.format = d.replace(/m/g, "M");
            e.lang = f;
            this.show(a, e)
        },
        onWndClick: function() {}
	}
})(jQuery);
$css.datePicker=jQuery.DatePicker;