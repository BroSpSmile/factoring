/**
 * 报表
 */
common.pageName = "assets";
common.openName = [ '7' ];

var vue = new Vue({
    el : '#assets',
    data : {
    	list:[
    		{
    			zctitle:"应收账款",
    			zcamount:50000000,
    			zctotalamount:50000000,
    			fztitle:"短期借款",
    			fzamount:6000000,
    			fztotalamount:6000000
    		},{
    			zctitle:"应收保理费",
    			zcamount:4200000,
    			zctotalamount:4200000,
    			fztitle:"应付票据",
    			fzamount:6000000,
    			fztotalamount:6000000
    		},{
    			zctitle:"长期应收保理费",
    			zcamount:50000000,
    			zctotalamount:"",
    			fztitle:"长期借款",
    			fzamount:4200000,
    			fztotalamount:4200000
    		}
    	],
    	tableColumns:[
    		{
    	        title: '资产',
    	        key: 'zctitle',
    	        align: 'center'
    	    },{
    	        title: '期末余额',
    	        key: 'zcamount',
    	        align: 'center'
    	    },{
    	        title: '年初余额',
    	        key: 'zctotalamount',
    	        align: 'center'
    	    },{
    	        title: '负债和所有者权益(或股东权益)',
    	        key: 'fztitle',
    	        width:240,
    	        align: 'center'
    	    },{
    	        title: '期末余额',
    	        key: 'fzamount',
    	        align: 'center'
    	    },{
    	        title: '年初余额',
    	        key: 'fztotalamount',
    	        align: 'center'
    	    }
    	]
    },
    created : function() {
    },
    methods : {
    }
});