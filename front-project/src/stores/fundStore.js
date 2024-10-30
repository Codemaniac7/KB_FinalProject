import { defineStore } from 'pinia';

export const useFundStore = defineStore('fundStore', {
    state: () => ({
        fundDetails: {},
    }),
    actions: {
        setFundDetails(details) {
            this.fundDetails = details;
        },
    },
});