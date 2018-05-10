/*
 * Created on 21.03.2014
 */
package ch.fhnw.algd.sortalgs;

import ch.fhnw.algd.sortdemo.framework.SortAlg;
import ch.fhnw.algd.sortdemo.framework.SortData;

public class HeapSort implements SortAlg {
	private int sizeOfSortedSection = 0;

	@Override
	public void run(SortData data) {
		//data: wird von Applikation drum herum zur Verfügung gestellt.

		//Todo: data via Floyd zu einem MaxHeap, dann mit HeapSort sortieren.
		//data.swap(indices_a, indices_b);
		System.out.println("creating max heap.");
		toFloydMaxHeap(data);
		System.out.println("max heap created.");
		sort(data);
		System.out.println("finished sorting. Did it work?");
	}

	private void toFloydMaxHeap(SortData data) {
		//Explanation: https://www.youtube.com/watch?v=ixdWTKWSz7s
		// n/2 = first leaf node. n/2 - 1 = last inner node and start index.
		for (int i = (data.size() / 2 - 1); i >= 0; --i) {
			System.out.println(" node nr. " + i);
			siftDown(data, i);
		}
	}

	private void siftDown(SortData data, int start) {
		int parent = start;
		int child = indexOfLargerChild(data, parent);
		// Vergleich der Werte mit dem aktuellen Index i und j: d[i] < d[j]
		while (child < data.size() - sizeOfSortedSection && data.less(parent, child) ) {
			System.out.println("data.less(parent, child): " + data.less(parent, child));
			data.swap(parent, child);
			parent = child;
			child = indexOfLargerChild(data, parent);
		}
	}

	private int indexOfLargerChild(SortData data, int current) {
		int child = 2 * current + 1; //left child
		if (child + 1 < data.size() && data.less(child, child + 1)) {
			child++;	//right child
		}
		return child;
	}

	private void sort(SortData data) {
		//Explenation: https://www.youtube.com/watch?v=2DmK_H7IdTo
		int i = data.size() - 1;
		while ( i >= 0) {
			data.swap(0, i);
			//heap verkleinern
			--i;
			System.out.println("heap size reduced to: "+ (i + 1) +" Reordering heap.");
			++sizeOfSortedSection;
			System.out.println("sorted section's size: " + sizeOfSortedSection);
			siftDown(data, 0);
		}
		//hotfix. Todo: find bug why the elements at index 0 and 1 fail to be sorted.
		if (data.less(1, 0)) data.swap(0, 1);
	}
}
