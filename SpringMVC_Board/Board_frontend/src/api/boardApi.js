import api from '@/api';

const BASE_URL = '/api/board';

const headers = { 'Content-Type': 'multipart/form-data' };



export default {
  async getList(params) {
    
    const { data } = await api.get(BASE_URL, { params });
    console.log('BOARD GET LIST: ', data);
    return data;
  },

  async get(no) {
    const { data } = await api.get(`${BASE_URL}/${no}`);
    console.log('BOARD GET', data);
    return data;
  },
  

  async getTypes() {
    const { data } = await api.get(`${BASE_URL}/types`);
    console.log('TYPES GET', data);
    return data;
  },

  async create(article) {
    const formData = new FormData();
  
    formData.append('title', article.title);
    formData.append('type', article.type);
    formData.append('writer', article.writer);
    formData.append('content', article.content);

    if (article.files) {
      for (let i = 0; i < article.files.length; i++) {
        formData.append('files', article.files[i]);
      }
    }

    const { data } = await api.post(BASE_URL, formData, { headers });
    console.log('BOARD POST: ', data);
    return data;
  },

  async update(article) {
    const formData = new FormData();
    formData.append('bno', article.bno);
    formData.append('type', article.type);
    formData.append('title', article.title);
    formData.append('content', article.content);

    if (article.files) {
      for (let i = 0; i < article.files.length; i++) {
        formData.append('files', article.files[i]);
      }
    }
    console.log('BOARD PUT prev: ', article.files);
    const { data } = await api.put(`${BASE_URL}/${article.bno}`, formData, { headers });
    console.log('BOARD PUT: ', data);
    return data;
  },

  async delete(no) {
    const { data } = await api.delete(`${BASE_URL}/${no}`);
    console.log('BOARD DELETE: ', data);
    return data;
  },

  async deleteAttachment(no) {
    const { data } = await api.delete(`${BASE_URL}/attachment/${no}`);
    console.log('ATTACHMENT DELETE: ', data);
    return data;
  },

  async sendReply(reply) {
    const { data } = await api.post(`${BASE_URL}/reply/${reply.bno}`, reply);
    console.log('SEND REPLY', reply);
    return data;
  },
  
  async deleteReply(rno) {
    const { data } = await api.delete(`${BASE_URL}/reply/${rno}`);
    console.log('DELETE REPLY', rno);
    return data;
  },

};
