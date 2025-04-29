import React, { useState, useEffect } from "react";

import '../App.css';

import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText


} from "../layout/Best.style.js";
import logo from "../assets/logo.png";

const Best = () => {

    // 찜 목록 상태 관리
    const [favorites, setFavorites] = useState([]);

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

    

    // 아이템이 찜 목록에 있는지 확인
    const isFavorite = (id) => favorites.includes(id);

    return (
        <ContentsWrapper>
            <MainContents>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like
                            liked={isFavorite(1)}
                            onClick={() => toggleFavorite(1)}
                        >
                            {isFavorite(1) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            레트로 체크자켓
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like
                            liked={isFavorite(2)}
                            onClick={() => toggleFavorite(2)}
                        >
                            {isFavorite(2) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            풀오버 후드티
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like
                            liked={isFavorite(3)}
                            onClick={() => toggleFavorite(3)}
                        >
                            {isFavorite(3) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                        </Like>
                        <ClothesText>
                            빈티지 청바지
                        </ClothesText>
                    </Content>
                </Card>


            </MainContents>
        </ContentsWrapper>
    );
}

export default Best;
