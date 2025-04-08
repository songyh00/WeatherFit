import React from 'react';
import '../App.css';
import {
    ContentsWrapper,
    Selected,
    ResultBox
} from "../layout/CustomerServiceCenter.Style.js";
import { useState } from 'react';

const CustomerServiceCenter = () => {

    const [selectedOption, setSelectedOption] = useState('');
    const [Value1, setValue1] = useState('체크박스를 선택해주세요');
    const [Value2, setValue2] = useState('회원가입이 되시지 않으실 경우엔 아이디와 이메일의 중복검사를 제대로 실행해주세요. \n이메일은 asdf@asdf.com 형식으로 작성해주세요');
    const [Value3, setValue3] = useState('로그인이 되지않을시 하단의 전화번호로 연락주십시오');
    const [Value4, setValue4] = useState('저희 사이트는 \n3, 4, 5월은 봄, 6, 7, 8월은 여름, \n9, 10, 11월은 가을, 12 ,1 ,2를 겨울로 나누고 있습니다.');


    const handleSelectChange = (event) => {
        setSelectedOption(event.target.value);
    };

    return (
        <ContentsWrapper>
            <Selected>
                <select onChange={handleSelectChange}>
                    <option value={Value1}>이곳을 클릭하여 선택하세요</option>
                    <option value={Value2}>회원가입이 되지 않아요</option>
                    <option value={Value3}>회원가입을 했는데 로그인이 되지 않아요</option>
                    <option value={Value4}>지금 계절에 맞는 옷이 뜨지 않아요</option>
                </select>
            </Selected>

            <ResultBox>{selectedOption ? selectedOption : '체크박스를 선택해주세요'}</ResultBox>

        </ContentsWrapper>
    );
}

export default CustomerServiceCenter;
