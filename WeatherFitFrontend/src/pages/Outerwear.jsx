import React, { useState, useEffect } from "react";
import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText,
    Wimg
} from "../layout/Best.style.js";

const Outerwear = ({ recommendationData, isLoading }) => {
    const [favorites, setFavorites] = useState([]);

    useEffect(() => {
        const storedFavorites = JSON.parse(localStorage.getItem('favorites')) || [];
        setFavorites(storedFavorites);
    }, []);

    const toggleFavorite = (id) => {
        const updatedFavorites = favorites.includes(id)
            ? favorites.filter((favoriteId) => favoriteId !== id)
            : [...favorites, id];

        setFavorites(updatedFavorites);
        localStorage.setItem('favorites', JSON.stringify(updatedFavorites));
    };

    const isFavorite = (id) => favorites.includes(id);

    const renderCategoryCards = (list) => {
        return list.map((item, idx) => (
            <Card key={`${item.category}-${idx}`}>
                <Wimg>
                    <img
                        src={item.imageUrl}
                        alt={item.name}
                    />
                </Wimg>
                <Content>
                    <Like
                        liked={isFavorite(item.id)}
                        onClick={() => toggleFavorite(item.id)}
                    >
                        {isFavorite(item.id) ? '찜 했습니다 ❤️' : '찜하기 🤍'}
                    </Like>
                    <ClothesText>{item.name}</ClothesText>
                </Content>
            </Card>
        ));
    };

    const getOuterwears = () => {
        return recommendationData?.recommendedClothes?.filter(item => item.category === "아우터") || [];
    };

    const outerwearList = getOuterwears();

    return (
        <ContentsWrapper>
            <MainContents>
                {!isLoading && recommendationData && outerwearList.length > 0 && (
                    renderCategoryCards(outerwearList)
                )}

                {!isLoading && recommendationData && outerwearList.length === 0 && (
                    <p style={{ fontSize: "1.1rem", marginTop: "20px", textAlign: "center" }}>
                        아우터를 입기엔 날씨가 덥습니다.<br />
                        그래도 입으시려면 얇은 아우터를 추천드립니다 🙂
                    </p>
                )}
            </MainContents>
        </ContentsWrapper>
    );
};

export default Outerwear;
