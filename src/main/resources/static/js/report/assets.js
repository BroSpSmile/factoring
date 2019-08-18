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
    			fzamount:"-",
    			fztotalamount:"-"
    		},{
    			zctitle:"应收保理费",
    			zcamount:4200000,
    			zctotalamount:4200000,
    			fztitle:"应付票据",
    			fzamount:"-",
    			fztotalamount:"-"
    		},{
    			zctitle:"长期应收保理费",
    			zcamount:50000000,
    			zctotalamount:"-",
    			fztitle:"长期借款",
    			fzamount:"-",
    			fztotalamount:"-"
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
    	this.getAssets();
    },
    methods : {
    	getAssets:function(){
    		let _self = this;
    		let param = moment(this.queryMonth).format('YYYY-MM')
    		this.$http.get("/assets/"+param).then(function(response){
    			_self.list[0].zcamount = response.data[0].zcamount +"元";
    			_self.list[0].zctotalamount = response.data[0].zctotalamount +"元";
    			_self.list[1].zcamount = response.data[1].zcamount +"元";
    			_self.list[1].zctotalamount = response.data[1].zctotalamount +"元";
    			_self.list[2].zcamount = response.data[2].zcamount +"元";
    			_self.list[2].zctotalamount = response.data[2].zctotalamount +"元";
    		},function(error){
    			console.log(error);
    		})
    	}
    }
});