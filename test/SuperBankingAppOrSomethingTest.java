import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.ExcludeCategories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SuperBankingAppOrSomethingTest {


    @org.junit.Test
    public void A_new_account_is_empty() throws Exception {
        Account account = new Account( );

        assertThat(account.isEmpty(), equalTo( true ));
    }

    @Test
    public void An_account_cannot_be_overdrawn() throws Exception {
        Account account = new Account();

        Money money = new Money(1);
        try {
            account.withdraw( money );
        } catch (AccountCannotBeOverdrawn e) {
            return;
        }
        fail("Expected AccountCannotBeOverdrawn");

    }

    private class Account {
        public boolean isEmpty() {
            return true;
        }


        public void withdraw(Money money) {
            throw new AccountCannotBeOverdrawn();
        }
    }

    private class AccountCannotBeOverdrawn extends RuntimeException {
    }

    private class Money {
        public Money(int i) {

        }
    }
}
