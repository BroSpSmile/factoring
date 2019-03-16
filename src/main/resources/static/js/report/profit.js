/**
 * 报表
 */
common.pageName = "profit";
common.openName = [ '7' ];

var vue = new Vue({
    el : '#profit',
    data : {
    	list:[
    		{
    			zctitle:"营业收入",
    			zcamount:50000000,
    			zctotalamount:50000000
    		}
    	],
    	tableColumns:[
    		{
    	        title: '项目',
    	        key: 'zctitle',
    	        align: 'center'
    	    },{
    	        title: '本期发生额',
    	        key: 'zcamount',
    	        align: 'center'
    	    },{
    	        title: '累计发生额',
    	        key: 'zctotalamount',
    	        align: 'center'
    	    }
    	]
    },
    created : function() {
    },
    methods : {
    }
});