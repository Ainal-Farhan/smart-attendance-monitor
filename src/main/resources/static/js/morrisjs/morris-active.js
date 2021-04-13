// Dashboard 1 Morris-chart

Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2012',
            Low: 0,
            Normal: 0,
            High: 0
        }, {
            period: '2013',
            Low: 130,
            Normal: 100,
            High: 80
        }, {
            period: '2014',
            Low: 80,
            Normal: 60,
            High: 70
        }, {
            period: '2015',
            Low: 70,
            Normal: 200,
            High: 160
        }, {
            period: '2016',
            Low: 180,
            Normal: 150,
            High: 120
        }, {
            period: '2017',
            Low: 105,
            Normal: 100,
            High: 90
        },
         {
            period: '2018',
            Low: 250,
            Normal: 150,
            High: 200
        }],
        xkey: 'period',
        ykeys: ['Low', 'Normal', 'High'],
        labels: ['Low', 'Normal', 'High'],
        pointSize: 0,
        fillOpacity: 0.6,
        pointStrokeColors:['#edfb46', '#52973a', '#ff0000'],
        behaveLikeLine: true,
        gridLineColor: '#e0e0e0',
        lineWidth:0,
        hideHover: 'auto',
        lineColors: ['#edfb46', '#52973a', '#ff0000'],
        resize: true
        
    });

Morris.Area({
        element: 'extra-area-chart',
        data: [{
                    period: '2012',
                    Low: 0,
                    Normal: 0,
                    High: 0
                }, {
                    period: '2013',
                    Low: 50,
                    Normal: 15,
                    High: 5
                }, {
                    period: '2014',
                    Low: 20,
                    Normal: 50,
                    High: 65
                }, {
                    period: '2015',
                    Low: 60,
                    Normal: 12,
                    High: 7
                }, {
                    period: '2016',
                    Low: 30,
                    Normal: 20,
                    High: 120
                }, {
                    period: '2017',
                    Low: 25,
                    Normal: 80,
                    High: 40
                }, {
                    period: '2018',
                    Low: 10,
                    Normal: 10,
                    High: 10
                }


                ],
                lineColors: ['#edfb46', '#52973a', '#5ccf61'],
                xkey: 'period',
                ykeys: ['Low', 'Normal', 'High'],
                labels: ['Low', 'Normal', 'High'],
                pointSize: 0,
                lineWidth: 0,
                resize:true,
                fillOpacity: 0.8,
                behaveLikeLine: true,
                gridLineColor: '#e0e0e0',
                hideHover: 'auto'
        
    });
