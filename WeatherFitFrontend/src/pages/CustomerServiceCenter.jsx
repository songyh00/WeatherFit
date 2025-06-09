import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import '../App.css';
import {ContentsWrapper} from "../layout/mainPage.style.js";

// import {
//     ContentsWrapper,
//     Selected,
//     ResultBox
// } from "../layout/CustomerServiceCenter.Style.js";
// import { useState } from 'react';

const CustomerServiceCenter = () => {
    return (
        <ContentsWrapper style={{ width: '60%', margin: '50px auto' }}>
            <div className="accordion" id="accordionExample">
                {/* 회원가입이 되지 않아요 (기본으로 열려 있음) */}
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            회원가입이 되지 않아요
                        </button>
                    </h2>
                    <div id="collapseOne" className="accordion-collapse collapse show" data-bs-parent="#accordionExample">
                        <div className="accordion-body">
                            회원가입이 되지 않을 경우, <strong>아이디 및 이메일 중복 검사를 확인</strong>해주세요.<br/>
                            이메일은 <code>example@example.com</code> 형식으로 입력해야 합니다.
                        </div>
                    </div>
                </div>

                {/* 비밀번호를 잊어버렸어요 */}
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            비밀번호를 잊어버렸어요. 어떻게 해야 하나요?
                        </button>
                    </h2>
                    <div id="collapseTwo" className="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div className="accordion-body">
                            로그인 페이지에서 <strong>'비밀번호 찾기'</strong>를 클릭한 후, 가입하신 아이디와 이메일을 입력하세요.<br/>
                            그 후 비밀번호를 재설정 하시면 됩니다.
                        </div>
                    </div>
                </div>

                {/* 추천된 옷을 바로 구매할 수 있나요? */}
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                            추천된 옷을 바로 구매할 수 있나요?
                        </button>
                    </h2>
                    <div id="collapseThree" className="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div className="accordion-body">
                            아니오. 저희는 단순히 계절과 날씨에 맞는 옷을 추천해주는 서비스이며, 직접 구매 기능은 제공하지 않습니다.
                        </div>
                    </div>
                </div>

                {/* 어떻게 계절에 맞는 옷을 추천해주나요? */}
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                            어떻게 계절에 맞는 옷을 추천해주나요?
                        </button>
                    </h2>
                    <div id="collapseFour" className="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div className="accordion-body">
                            저희는 실시간 날씨의 평균 기온 데이터를 분석하여, <strong>적절한 옷차림</strong>을 추천합니다.<br/>
                        </div>
                    </div>
                </div>

                {/* 내 지역 날씨에 따라 추천이 달라지나요? */}
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                            내 지역 날씨에 따라 추천이 달라지나요?
                        </button>
                    </h2>
                    <div id="collapseFive" className="accordion-collapse collapse" data-bs-parent="#accordionExample">
                        <div className="accordion-body">
                            네! <strong>지정된 위치의 실시간 날씨</strong>를 기반으로 의상을 추천합니다.<br/>
                        </div>
                    </div>
                </div>
            </div>
        </ContentsWrapper>
    );
}

export default CustomerServiceCenter;
