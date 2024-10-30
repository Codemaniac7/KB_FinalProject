import api from '@/api';

const BASE_URL = '/api/member';
const headers = { 'Content-Type': 'multipart/form-data' };

export default {

//////////////  회원 정보（ａｕｔｈ） 조회   ///////////////////////

//   async getList(params) {
//       const { data } = await api.get(BASE_URL, { params });
//     console.log('AUTH GET LIST: ', data);
//     return data;
//   },

 ///////////////  회원 id중복 체크   ////////////////////////

  async checkId(id) {
    const { data } = await api.get(`${BASE_URL}/checkid/${id}`);
    console.log('AUTH GET CHECK ID', data);
    return data;
  },

 ///////////////  회원 정보 조회（ｕｓｅｒｎａｍｅ ＝＝ ｉｄ） ////////////////////////
  async get(id) {
    const { data } = await api.get(`${BASE_URL}/${id}`);
    console.log('AUTH GET', data);
    return data;
  },

  async getKakaoInfo(code) {
    const { data } = await api.get(`${BASE_URL}/kakaoInfo/${code}`);
    console.log('AUTH GET', data);
    return data;
  },

  async checkKakaoMember(kakaoId) {
    const {data} = await api.get(`${BASE_URL}/checkkakao/${kakaoId}`)
    return data;
  },

 ///////////////// 회원 정보 가입 //////////////////////////
  async create(member) {
    const formData = new FormData();
    formData.append('id', member.id);
    formData.append('password', member.password);
    formData.append('name', member.name);
    formData.append('email', member.email);
    if(member.kakaoId){
      formData.append('kakaoId', member.kakaoId);
    }


     // --------> 회원 정보 post방식 전송  //////////////////////////
    const { data } = await api.post(BASE_URL, formData, headers);

    console.log('AUTH POST: ', data);
    return data;
  },
 /////////////// 회원 정보 수정 ///////////////////////////////
  
  async update(member) {
    const formData = new FormData();
    formData.append('id', member.id);
    formData.append('name', member.name);
    formData.append('password', member.password);
    formData.append('email', member.email);

    const { data } = await api.put(`${BASE_URL}/${member.id}`, formData, headers);
    console.log('AUTH PUT: ', data);
    return data;
  },

 /////////////// 회원 탈퇴 ///////////////////////////////
  
  async delete(id) {
    const { data } = await api.delete(`${BASE_URL}/${id}`);
    console.log('AUTH DELETE: ', data);
    return data;
  },

 /////////////// 회원 암호 수정 ///////////////////////////////

  async changePassword(formData) {
    console.log('formData : ', formData);
    const { data } = await api.put(`${BASE_URL}/${formData}/changepassword`, formData);
    console.log('AUTH PUT: ', data);
    return data;
  },


};
