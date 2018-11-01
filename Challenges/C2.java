import java.util.*;

/**
	a) Stevilo primerjav:
		Iterativni algoritem: O(n), kjer je n velikost tabele. Ker se vedno sprehajamo cez celo tabelo in moramo primerjati elemente. Primerjav je n - 1 za tabelo velikosti n.
			Ampak, n - 1 primerjav je samo za iskanje minimuma ali maksimuma, za iskanje obeh stevil rabimo 2 * (n - 1) primerjav.
		Rekurzivni algoritem: Ce predpostavimo, da imamo tabelo velikosti 2^n in se vstavimo pri tabeli velikosti 2, potem:
			Visina drevesa je n. Ampak se vstavimo na predzadnjem nivoju, zato "visina" rekurzije je n - 1. Za vsak rekurzivni klic, ki je na
			predzadnjem nivoju potrebujemo 1 primerjavo. Na predzadnjem nivoju imamo 2^(n - 1) elementov, torej 2^(n - 1) primerjav. Za ostale
			n - 2 visini pa potrebujemo 2 primerjavi. Binarno drevo z visino n - 2 ima 2^(n - 1) - 1 elementov. Rabimo 2 * (2^(n - 1) - 1) =
			2^n - 2 primerjav. Torej, za celotni algoritem rabimo 2^(n - 1) + 2^n - 2 primerjav. Asimptoticna zhtevnost je O(2^n) = O(velikost tabele), kjer je n = log2(velikost tabele).
	b) Natacno formulo sem izracunal v podnalogi a). Zdaj jo bom samo dokazal z indukcijo.
		Iterativni algoritem: Stevilo primerjav p = n - 1, kjer je n velikost tabele. Za n = 1 => p = 0, ne rabimo primerjav, kjer imamo samo 1 element in ta element je max / min.
			Recimo da p = n - 1, za nek n > 0. Potem, za tabelo velikosti n + 1, rabimo se eno primerjavo da dolocimo ali zares, element, ki smo ga do sedaj poiskali, je max / min.
			Torej, za tabelo velikosti n + 1 rabimo (n - 1) primerjav za elemente do vkljucno predzadnjega, in se eno primerjavo za zadnji element.
			p' = (n - 1) + 1 = n za tabelo velikosti n + 1 => p' = (n + 1) - 1, in tako smo dokazali formulo.
		Rekurzivni algoritem: Recimo, da je velikost tabele 2^n. Ce uporabimo formulo za tabelo 2^1 = 2(n = 1), rabimo 2^(n - 1) + 2^n - 2 = 1 primerjavo.
			To je sicer logicno, ker za 2 elementa, rabimo samo 1 primerjavo da dolocimo min, pa drugi element je max. Recimo da to velja za tabelo velikosti 2^n.
			Potem, za tabelo velikosti 2^(n + 1): Stevilo primerjav za 2^n je 2^(n - 1) + 2^n - 2. Ce n povecamo za 1, se bo visina drevesa rekurzivnega izracuna
			povecala za 1. Za predzadnjem nivoju tega drevesa, potrebujemo samo 1 primerjavo, ker imamo samo 2 elementa, ki jih primerjamo. Torej, dodamo 2^n primerjav.
			Ampak, ker smo visino povecali za 1, potem se je tudi spremenila visina predzadnjega nivoja za velikosti n, in zdaj vsebuje 4 elemente (namesto 2), in rabimo
			se 2 primerjavi (namesto 1), torej rezultatu dodamo 2^(n - 1). Ce do sestejemo, dobimo 2^(n - 1) + 2^n - 2 + 2^n + 2^(n - 1) = 2 * 2^n + 2 * 2^(n - 1) - 2 = 
			2^(n + 1) + 2^n - 2 za tabelo velikosti n + 1, in to se ujema z formulo => induktivno smo dokazali, da je formula pravilna.
	c) Lahko pozenemo algoritem v tej kodi in dolocimo stevilo primerjav.
	d) Ja, stevilo primerjav se ujema. Velikost tabele je 16 = 2^4. Stevilo primerjav po formuli je 2^n + 2^(n - 1) - 2 = 2^4 + 2^3 - 2 = 22. In tudi program izracuna 22 primerjav.
**/

public class C2
{
	public static int cmp;
	public static final int[] arr = {3, 12, 7, 4, 13, 1, 14, 8, 6, 11, 9, 2, 15, 5, 16, 10};
	
	public static void iter()
	{
		int maxi = 0, mini = 0;
		int cmpx = 0, cmpn = 0;
		
		for (int i = 1; i < arr.length; i++) {
			if (arr[maxi] < arr[i]) maxi = i; cmpx++;
			if (arr[mini] > arr[i]) mini = i; cmpn++;
		}
		
		System.out.printf("Array length: %d ... it_min = %d ... it_max = %d ... it_cmp_min = %d ... it_cmp_max = %d\n", arr.length, arr[mini], arr[maxi], cmpn, cmpx);
	}
	
	public static int[] rec(int l, int r)
	{
		if (r == l) return new int[] {arr[l], arr[r]};
		
		if (r - l == 1) { cmp++;
			if (arr[l] < arr[r]) {
				return new int[] {arr[l], arr[r]};
			}
			return new int[] {arr[r], arr[l]};
		}
		
		int mid = (l + r) >> 1;
		int[] ls = rec(l, mid), rs = rec(mid + 1, r);
		
		int minv = 0, maxv = 0; cmp += 2;
		if (ls[0] < rs[0]) minv = ls[0];
		else			   minv = rs[0];
		
		if (ls[1] < rs[1]) maxv = rs[1];
		else			   maxv = ls[1];
		
		if (l == 0 && r == arr.length - 1)
			System.out.printf("Array length: %d ... rec_min = %d ... rec_max = %d ... rec_cmp = %d\n", arr.length, minv, maxv, cmp);
		
		return new int[] {minv, maxv};
	}
	
	public static void main(String[] args)
	{
		iter();
		rec(0, arr.length - 1);
	}
}