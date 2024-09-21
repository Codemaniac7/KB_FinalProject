import{i as n}from"./index-DRcVQfPh.js";const o="/api/board",p={"Content-Type":"multipart/form-data"},d={async getList(t){const{data:e}=await n.get(o,{params:t});return console.log("BOARD GET LIST: ",e),e},async get(t){const{data:e}=await n.get(`${o}/${t}`);return console.log("BOARD GET",e),e},async getTypes(){const{data:t}=await n.get(`${o}/types`);return console.log("TYPES GET",t),t},async create(t){const e=new FormData;if(e.append("title",t.title),e.append("type",t.type),e.append("writer",t.writer),e.append("content",t.content),t.files)for(let a=0;a<t.files.length;a++)e.append("files",t.files[a]);const{data:s}=await n.post(o,e,{headers:p});return console.log("BOARD POST: ",s),s},async update(t){const e=new FormData;if(e.append("bno",t.bno),e.append("type",t.type),e.append("title",t.title),e.append("content",t.content),t.files)for(let a=0;a<t.files.length;a++)e.append("files",t.files[a]);console.log("BOARD PUT prev: ",t.files);const{data:s}=await n.put(`${o}/${t.bno}`,e,{headers:p});return console.log("BOARD PUT: ",s),s},async delete(t){const{data:e}=await n.delete(`${o}/${t}`);return console.log("BOARD DELETE: ",e),e},async deleteAttachment(t){const{data:e}=await n.delete(`${o}/attachment/${t}`);return console.log("ATTACHMENT DELETE: ",e),e},async sendReply(t){const{data:e}=await n.post(`${o}/reply/${t.bno}`,t);return console.log("SEND REPLY",t),e},async deleteReply(t){const{data:e}=await n.delete(`${o}/reply/${t}`);return console.log("DELETE REPLY",t),e}};export{d as b};