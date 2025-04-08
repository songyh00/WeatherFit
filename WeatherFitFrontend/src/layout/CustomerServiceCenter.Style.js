import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export const ContentsWrapper = styled.div`
    padding: 176px 0 0 0;
    width: 100%;
    margin: 0 auto;
    box-sizing: border-box;
    white-space: pre-wrap; //줄바꿈 되도록 하는 코드
`;

export const Selected = styled.div`
    
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 40vh;
    background-color: #f4f6f9;
    padding: 0 20px;
    max-width: 1000px;
    margin: 0 auto;
    border-radius: 10px;
    width: 100%;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);

    
    
    select {
        width: 100%;
        max-width: 600px;
        padding: 20px 24px;
        font-size: 20px;
        border: 2px solid #ccc;
        border-radius: 14px;
        background-color: #fff;
        background-image: url("data:image/svg+xml;utf8,<svg fill='%23333' height='28' viewBox='0 0 24 24' width='28' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
        //체크박스 오른쪽에 있는 화살표 이미지
        background-repeat: no-repeat;
        background-position: right 20px center;
        background-size: 20px 20px;
        appearance: none;
        -webkit-appearance: none;
        -moz-appearance: none;
        cursor: pointer;
        transition: border-color 0.3s, box-shadow 0.3s;
        
    }

    & > option {
        font-size: 22px;
        padding: 12px 20px;
       
    }
   
`;

export const ResultBox = styled.div`
  margin: 0 auto;
  padding: 16px 20px;
  font-size: 20px;
  color: #333;
  background-color: #f0f4f8;
  border-left: 6px solid #4a90e2;
  border-radius: 10px;
  max-width: 1000px;
    
  width: 100%;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    
    
`;