import React, { useState, useEffect } from "react";
import '../App.css';
import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText, Wimg
} from "../layout/Best.style.js";
import logo from "../assets/logo.png";
import fit from "../assets/웨더핏 옷사진/웨더핏 상의/남자 반팔(1) 로고반팔 그레이.jpg";

const Consultation = () => {

    const [favorites, setFavorites] = useState([]);
    const [fits, setFits] = useState("상의 임시데이터");

    // localStorage에서 찜 목록 불러오기
    useEffect(() => {
        const storedFavorites = JSON.parse(localStorage.getItem('favorites')) || [];
        setFavorites(storedFavorites);
    }, []);

    // 찜 상태 토글
    const toggleFavorite = (id) => {
        const updatedFavorites = favorites.includes(id)
            ? favorites.filter((favoriteId) => favoriteId !== id)
            : [...favorites, id];

        // 찜 목록을 localStorage에 저장
        setFavorites(updatedFavorites);
        localStorage.setItem('favorites', JSON.stringify(updatedFavorites));
    };

    const isFavorite = (id) => favorites.includes(id);


    return (
        <ContentsWrapper>
            <MainContents>

                <Card>
                    <Wimg>
                        <img src={fit} alt="WeatherFit Logo" />
                    </Wimg>
                    <Content>
                        <Like
                            liked={isFavorite(1)}
                            onClick={() => toggleFavorite(1)}
                        >
                            {isFavorite(1) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            {fits}
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <Wimg>
                        <img src={fit} alt="WeatherFit Logo" />
                    </Wimg>
                    <Content>
                        <Like
                            liked={isFavorite(2)}
                            onClick={() => toggleFavorite(2)}
                        >
                            {isFavorite(2) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            {fits}
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <Wimg>
                        <img src={fit} alt="WeatherFit Logo" />
                    </Wimg>
                    <Content>
                        <Like
                            liked={isFavorite(3)}
                            onClick={() => toggleFavorite(3)}
                        >
                            {isFavorite(3) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            {fits}
                        </ClothesText>
                    </Content>
                </Card>


            </MainContents>
        </ContentsWrapper>
    );
}

export default Consultation;
