import{i as n}from"./index-DRcVQfPh.js";const o="/api/member",d={"Content-Type":"multipart/form-data"},p={async checkId(t){const{data:a}=await n.get(`${o}/checkid/${t}`);return console.log("AUTH GET CHECK ID",a),a},async get(t){const{data:a}=await n.get(`${o}/${t}`);return console.log("AUTH GET",a),a},async create(t){const a=new FormData;a.append("id",t.id),a.append("password",t.password),a.append("name",t.name),a.append("email",t.email),t.avatar&&a.append("avatar",t.avatar);const{data:s}=await n.post(o,a,d);return console.log("AUTH POST: ",s),s},async update(t){const a=new FormData;a.append("id",t.id),a.append("name",t.name),a.append("password",t.password),a.append("email",t.email),t.avatar&&a.append("avatar",t.avatar);const{data:s}=await n.put(`${o}/${t.id}`,a,d);return console.log("AUTH PUT: ",s),s},async delete(t){const{data:a}=await n.delete(`${o}/${t}`);return console.log("AUTH DELETE: ",a),a},async changePassword(t){console.log("formData : ",t);const{data:a}=await n.put(`${o}/${t.id}/changepassword`,t);return console.log("AUTH PUT: ",a),a}};export{p as a};
