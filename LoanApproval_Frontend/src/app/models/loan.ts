import { User } from "./user";

export class LoanApplication {
    id?: number;
    loanAmount: number = 0;
    loanType: string = '';
    status: string = '';
    statusReason: string = '';
    user: User;

    constructor(user: User) {
        this.user = user;
        this.id = user.id ?? 0; // Use 0 if user.id is undefined
    }
}
