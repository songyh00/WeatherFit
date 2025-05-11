import React, { useState, useEffect } from "react";
import '../App.css';
import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText,
    CategoryMenu,
    CategoryButton
} from "../layout/Outerwear.style.js";
import logo from "../assets/logo.png";

const items = [
    { id: 1, name: "아우터 1", category: "자켓" },
    { id: 2, name: "아우터 2", category: "자켓" },
    { id: 3, name: "아우터 3", category: "가디건/집업" },
    { id: 4, name: "아우터 4", category: "가디건/집업" },
    { id: 5, name: "아우터 5", category: "블레이저" },
    { id: 6, name: "아우터 6", category: "블레이저" },
    { id: 7, name: "아우터 7", category: "패딩" },
    { id: 8, name: "아우터 8", category: "패딩" },
    { id: 9, name: "아우터 9", category: "코트" },
    { id: 10, name: "아우터 10", category: "코트" }
];

const categories = ["전체", "자켓","가디건/집업", "블레이저", "패딩", "코트"];

const Outerwear = () => {
    const [favorites, setFavorites] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState("전체");

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

    const filteredItems = selectedCategory === "전체"
        ? items
        : items.filter(item => item.category === selectedCategory);

    return (
        <ContentsWrapper>
            <CategoryMenu>
                {categories.map(cat => (
                    <CategoryButton
                        key={cat}
                        active={selectedCategory === cat}
                        onClick={() => setSelectedCategory(cat)}
                    >
                        {cat}
                    </CategoryButton>
                ))}
            </CategoryMenu>

            <MainContents>
                {filteredItems.map(item => (
                    <Card key={item.id}>
                        <img src={logo} alt="WeatherFit Logo" />
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
                ))}
            </MainContents>
        </ContentsWrapper>
    );
};

export default Outerwear;
