import{_ as N,b as A,u as B,a as F,c as y,o as c,e as r,f as t,t as s,h as d,x as b,F as f,s as k,g as w,w as L,v as M,z as S,A as V}from"./index-Bgv5Leqj.js";import{b as v}from"./boardApi-D5N8Ft7t.js";import{h as g}from"./moment-C5S46NFB.js";import"./index-Da50IQAQ.js";const E=async i=>{try{const l=document.createElement("a");l.href=i,document.body.appendChild(l),l.click(),document.body.removeChild(l)}catch(l){console.error(l)}},n=i=>(S("data-v-ed1a9fdc"),i=i(),V(),i),T={class:"my-3 d-flex justify-content-between"},$=n(()=>t("i",{class:"fa-solid fa-user"},null,-1)),j=n(()=>t("i",{class:"fa-regular fa-clock"},null,-1)),H=n(()=>t("hr",null,null,-1)),P={class:"text-end"},U=["onClick"],z=n(()=>t("i",{class:"fa-solid fa-paperclip"},null,-1)),O={class:"content"},G={class:"my-5"},J=n(()=>t("i",{class:"fa-solid fa-list"},null,-1)),K=n(()=>t("i",{class:"fa-regular fa-pen-to-square"},null,-1)),Q=n(()=>t("i",{class:"fa-solid fa-trash-can"},null,-1)),W={class:"row mt-2"},X={class:"col-11"},Z=n(()=>t("label",{class:"form-label"},"댓글",-1)),tt={class:"col-1"},et=n(()=>t("label",{class:"form-label"},[d("   "),t("span",{class:"text-body-secondary"})],-1)),ot={class:"table table-striped mt-4"},st=n(()=>t("thead",null,[t("tr",null,[t("th",{style:{width:"60px"}},"No"),t("th",null,"댓글 내용"),t("th",{style:{width:"150px"}},"작성자"),t("th",{style:{width:"120px"}},"작성일"),t("th",{style:{width:"80px"}})])],-1)),lt={key:0},at=["onClick"],nt={__name:"BoardDetailPage",setup(i){const l=A(),p=B(),_=F(),u=p.params.no,a=y({}),h=y(""),x=()=>{_.push({name:"board/list",query:p.query})},C=()=>{_.push({name:"board/update",params:{no:u},query:p.query})},D=async m=>{const o="/api/board/download/"+m;console.log(o),await E(o)},I=async()=>{confirm("삭제할까요?")&&(await v.delete(u),_.push({name:"board/list",query:p.query}))};(async()=>{a.value=await v.get(u),console.log("DETAIL",a.value)})();const Y=async()=>{if(h.value==null||h.value.length==0){alert("내용이 없습니다.");return}const m={bno:u,content:h.value,writer:l.id},o=await v.sendReply(m);console.log(o),o!=null?(alert("댓글이 등록 되었습니다."),_.go("/board/detail/"+u)):alert("댓글 등록에 실패 하였습니다.")},q=async m=>{const o=await v.deleteReply(m);console.log(o),o!=null?(alert("댓글이 삭제 되었습니다."),_.go("/board/detail/"+u)):alert("댓글 삭제에 실패 하였습니다.")};return(m,o)=>(c(),r(f,null,[t("h1",null,s(a.value.title),1),t("div",T,[t("div",null,[$,d(" "+s(a.value.memberName)+" ("+s(a.value.memberId)+") ",1)]),t("div",null,[j,d(" "+s(b(g)(a.value.updateDate).format("YYYY-MM-DD HH:mm")),1)])]),H,t("div",P,[(c(!0),r(f,null,k(a.value.boardAttachFileList,e=>(c(),r("div",{key:e.fno,class:"attach"},[t("span",{onClick:R=>D(e.fno)},[z,d(" "+s(e.originalFilename),1)],8,U)]))),128))]),t("div",O,s(a.value.content),1),t("div",G,[t("button",{class:"btn btn-primary me-2",onClick:x},[J,d(" 목록")]),b(l).id==a.value.memberId?(c(),r(f,{key:0},[t("button",{class:"btn btn-primary me-2",onClick:C},[K,d(" 수정")]),t("button",{class:"btn btn-danger",onClick:I},[Q,d(" 삭제")])],64)):w("",!0)]),t("div",W,[t("div",X,[Z,L(t("input",{"onUpdate:modelValue":o[0]||(o[0]=e=>h.value=e),type:"text",class:"form-control",placeholder:"내용을 입력하세요."},null,512),[[M,h.value]])]),t("div",tt,[et,t("button",{class:"form-control btn btn-primary",onClick:o[1]||(o[1]=e=>Y())},"작성")])]),t("table",ot,[st,t("tbody",null,[(c(!0),r(f,null,k(a.value.replyList,e=>(c(),r("tr",{key:e.rno},[t("td",null,s(e.rno),1),t("td",null,s(e.content),1),t("td",null,s(e.memberName)+"("+s(e.memberId)+")",1),t("td",null,s(b(g)(e).format("YYYY-MM-DD")),1),t("td",null,[e.memberId==b(l).id?(c(),r("span",lt,[t("button",{class:"btn btn-close",onClick:R=>q(e.rno)},null,8,at)])):w("",!0)])]))),128))])])],64))}},mt=N(nt,[["__scopeId","data-v-ed1a9fdc"]]);export{mt as default};
