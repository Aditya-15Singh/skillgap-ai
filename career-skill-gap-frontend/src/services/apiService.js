import axiosInstance from '../utils/axiosConfig';

export const profileService = {
    getProfile: async () => {
        const response = await axiosInstance.get('/profile');
        return response.data;
    },

    createOrUpdateProfile: async (profileData) => {
        const response = await axiosInstance.post('/profile', profileData);
        return response.data;
    }
};

export const analysisService = {
    getSkillGap: async () => {
        const response = await axiosInstance.get('/analysis/skill-gap');
        return response.data;
    },

    getLearningRoadmap: async () => {
        const response = await axiosInstance.get('/analysis/roadmap');
        return response.data;
    }
};

export const publicService = {
    getAllSkills: async () => {
        const response = await axiosInstance.get('/public/skills');
        return response.data;
    },

    getAllRoles: async () => {
        const response = await axiosInstance.get('/public/roles');
        return response.data;
    }
};

export const progressService = {
    toggleProgress: async (resourceId) => {
        const response = await axiosInstance.post(`/progress/${resourceId}`);
        return response.data;
    },

    getCompletedResources: async () => {
        const response = await axiosInstance.get('/progress');
        return response.data;
    },

    getSummary: async () => {
        const response = await axiosInstance.get('/progress/summary');
        return response.data;
    }
};

export const careerGoalService = {
    getGoal: async () => {
        const response = await axiosInstance.get('/career-goal');
        return response.data;
    },
    createGoal: async (goalData) => {
        const response = await axiosInstance.post('/career-goal', goalData);
        return response.data;
    },
    deleteGoal: async () => {
        const response = await axiosInstance.delete('/career-goal');
        return response.data;
    }
};
