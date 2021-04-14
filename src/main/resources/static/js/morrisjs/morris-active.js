// Dashboard Morris-chart

var attStatusGraph;
var attLineGraph;

initAttendanceStatusGraph();
initLineGraph();

function attStatusGraphData(data) {
    const priorBy30Days = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000);
    var j = 0;
    var gData = [];
    for(var i = priorBy30Days, count = 0; i <= Date.now(); i = new Date(i.getTime() + 24 * 60 * 60 * 1000), count++) {
        const date = i.toISOString().slice(0, 10);
        
        // L = Low, N = Normal, H = High
        var attL = 0;
        var attN = 0;
        var attH = 0;
        
        if(i === priorBy30Days) j = 0;

        for(j; j < data.length; j++) {
            if(data[j].date === date) {
                switch(data[j].status.toLowerCase()) {
                    case 'low':
                        attL++;
                        break;
                    case 'normal':
                        attN++;
                        break;
                    case 'high':
                        attH++;
                        break;
                }
            }
            else break;
        }

        const dataEach = {
            "date"   : date,
            "Normal"    : attN,
            "High" : attH,
            "Low"   : attL
        };
        gData.push(dataEach);
    }

    return gData;
}

function attLineGraphData(data) {
    const priorBy30Days = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000);
    var j = 0;
    var gData = [];
    for(var i = priorBy30Days, count = 0; i <= Date.now(); i = new Date(i.getTime() + 24 * 60 * 60 * 1000), count++) {
        const date = i.toISOString().slice(0, 10);
        
        var attAll = 0;
        
        if(i === priorBy30Days) j = 0;

        for(j; j < data.length; j++) {
            if(data[j].date === date) {
                attAll++;
            }
            else break;
        }

        const dataEach = {
            "Date"   : date,
            "Total Attendance"    : attAll
        };
        gData.push(dataEach);
    }
    return gData;
}

function initLineGraph() {
    fetchlast30DaysAttendance().then(data => {
        var gLineData = attLineGraphData(data);
        console.table(gLineData);
    
        const labelsLine = ['Total Attendance'];
        attLineGraph = setMorisLineChart(
            'extra-area-chart',
            gLineData,
            ['blue'],
            'Date',
            labelsLine,
            labelsLine,
            0, 
            1, 
            true,
            true,
            '#e0e0e0',
            true
        );
    });
}

function initAttendanceStatusGraph() {
    fetchlast30DaysAttendance().then(data => {
    
        var gStatusData = attStatusGraphData(data);
        console.table(gStatusData);

        const labelsStatus = ['Normal', 'High', 'Low'];
        attStatusGraph = setMorisAreaChart(
            'morris-area-chart', 
            gStatusData, 
            'date',
            labelsStatus,
            labelsStatus,
            0,
            0.8,
            ['green', 'red','yellow'],
            true,
            '#e0e0e0',
            0,
            'auto',
            ['green', 'red','yellow'],
            true
        );
    });
}

function refreshLineGraph() {
    fetchlast30DaysAttendance().then(data => {
        
        attLineGraph.setData([{
            "Date" : "",
            "Total Attendance"  : 0,
        }]);
        
        var gData = attLineGraphData(data);
    
        attLineGraph.setData(gData);
    });
}

function refreshAttendanceStatusGraph() {
    fetchlast30DaysAttendance().then(data => {
        
        attStatusGraph.setData([{
            "date" : "",
            "Normal"  : 0,
            "High"  : 0,
            "Low"  : 0,
        }]);
        
        var gData = attStatusGraphData(data);
    
        // console.table(gData);
        attStatusGraph.setData(gData);
    });
}

async function fetchlast30DaysAttendance() {
    const response = await fetch('https://stars-smart-attendance.herokuapp.com/api/attendance/last30days');
    const attendance = await response.json();
    return attendance;
}

function setMorisAreaChart(e, d, x, y, l, ps, fo, psc, bll, glc, lw, hd, ln, r) {
    return Morris.Area({
        element: e,
        data: d,
        xkey: x,
        ykeys: y,
        labels: l,
        pointSize: ps,
        fillOpacity: fo,
        pointStrokeColors:psc,
        behaveLikeLine: bll,
        gridLineColor: glc,
        lineWidth:lw,
        hideHover: hd,
        lineColors: ln,
        resize: r
    });
}

function setMorisLineChart(e, d, lc, x, y, l, ps, lw, r, bll, glc, hh) {
    return Morris.Line({
        element: e,
        data: d,
        lineColors: lc,
        xkey: x,
        ykeys: y,
        labels: l,
        pointSize: ps,
        lineWidth: lw,
        resize:r,
        behaveLikeLine: bll,
        gridLineColor: glc,
        hideHover: hh,
        lineWidth:1,
    });
}
