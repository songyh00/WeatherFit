export const getSeason = () => {
    const month = new Date().getMonth() + 1;
    if (month >= 3 && month <= 5) return "spring";
    if (month >= 6 && month <= 8) return "summer";
    if (month >= 9 && month <= 11) return "autumn";
    return "winter";
};

export const theme = {
    spring: { borderColor: "#FFB6C1", bgColor: "#FFF0F5", focusColor: "#FF69B4" },
    summer: { borderColor: "#4FC3F7", bgColor: "#E0F7FA", focusColor: "#0288D1" },
    autumn: { borderColor: "#FF8A65", bgColor: "#FFF3E0", focusColor: "#D84315" },
    winter: { borderColor: "#90A4AE", bgColor: "#ECEFF1", focusColor: "#455A64" }
};