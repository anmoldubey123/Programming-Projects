public class SieveSolver {
    public static void main(String args[]) {
        int num = 1000;
        boolean prime[] = new boolean[num + 1];
        for(int i = 0; i < num; i++)
            prime[i] = true;
        for(int j = 2; (j * j) <= num; j++)
        {
            if(prime[j] == true)
            {
                for(int i = j * j; i <= num; i += j)
                    prime[i] = false;
            }
        }
        for(int i = 2; i <= num; i++)
        {
            if(prime[i] == true)
                System.out.println(i + " ");
        }
    }
}