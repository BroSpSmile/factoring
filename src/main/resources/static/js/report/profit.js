/**
 * 报表
 */
common.pageName = "profit";
common.openName = [ '7' ];

var vue = new Vue({
    el : '#profit',
    data : {
    	queryMonth:new Date(),
    	list:[
    		{
    			zctitle:"营业收入",
    			zcamount:0.00,
    			zctotalamount:0.00
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
    	this.getProfit();
    },
    methods : {
    	getProfit:function(){
    		let _self = this;
    		let param = moment(this.queryMonth).format('YYYY-MM')
    		this.$http.get("/profit/"+param).then(function(response){
    			_self.list[0].zcamount = response.data.monthProfit +"元";
    			_self.list[0].zctotalamount = response.data.yearProfit +"元";
    		},function(error){
    			console.log(error);
    		})
    	}
    }
});