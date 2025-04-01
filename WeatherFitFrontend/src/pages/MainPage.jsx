import React from 'react';
import '../App.css';
import {
    ContentsWrapper,
    ContentsTitle,
    Mainimage
} from "../layout/mainPage.style.js";
import Mainillustration from "../assets/Mainillustration.png";
import Subillustration from "../assets/Subillustration.jpg";
import ServiceInformation from "../assets/ServiceInformation.png";


const MainPage = () => {
    return (
        <ContentsWrapper>
            <ContentsTitle>
            <h1>계절이 바뀔때 마다, 무슨 옷 입을지 고민되지 않으세요?</h1>


            <Mainimage>
            <img src={Mainillustration} alt="Mainillustration" />
            </Mainimage>

            <h1>옷 고를때 너무 고민하지마세요!<br/><br/>지금 계절에 맞는 BEST 옷 추천부터<br/>
                상의, 하의, 아우터까지 대신 골라드립니다!</h1>

            <Mainimage>
                <img src={Subillustration} alt="Subillustration" />
            </Mainimage>

                <h1>안녕하세요 저희는 'WeatherFit' 입니다</h1>

                <Mainimage>
                    <img src={ServiceInformation} alt="Subillustration" />
                </Mainimage>



            </ContentsTitle>



        </ContentsWrapper>
    );
}

export default MainPage;
