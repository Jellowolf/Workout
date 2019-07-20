package com.personal.jello.workout.database;

import android.app.Application;
import android.os.AsyncTask;

import com.personal.jello.workout.dataAccess.WeightTrainingDao;
import com.personal.jello.workout.models.WeightTrainingRecord;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutRepository {
    private WeightTrainingDao weightTrainingDao;
    private List<WeightTrainingRecord> records;

    public WorkoutRepository(Application application) {
        WorkoutDatabase db = WorkoutDatabase.getWorkoutDatabase(application);
        weightTrainingDao = db.weightTrainingDao();
        records = getAllRecords();
    }


    public List<WeightTrainingRecord> getAllRecords() {
        try {
            return new getAllAsyncTask(weightTrainingDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<WeightTrainingRecord>> {
        private WeightTrainingDao weightTrainingAsyncTaskDao;

        getAllAsyncTask(WeightTrainingDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected List<WeightTrainingRecord> doInBackground(final Void... params) {
            return weightTrainingAsyncTaskDao.getAll();
        }
    }

    public void insertRecord(WeightTrainingRecord record) {
        new insertAsyncTask(weightTrainingDao).execute(record);
    }

    private static class insertAsyncTask extends AsyncTask<WeightTrainingRecord, Void, Void> {
        private WeightTrainingDao weightTrainingAsyncTaskDao;

        insertAsyncTask(WeightTrainingDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecord... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateRecord(WeightTrainingRecord record) {
        new updateAsyncTask(weightTrainingDao).execute(record);
    }

    private static class updateAsyncTask extends AsyncTask<WeightTrainingRecord, Void, Void> {
        private WeightTrainingDao weightTrainingAsyncTaskDao;

        updateAsyncTask(WeightTrainingDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingRecord... params) {
            weightTrainingAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
